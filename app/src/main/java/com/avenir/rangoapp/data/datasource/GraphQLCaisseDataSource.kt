package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CaisseModel
import com.avenir.rangoapp.data.models.CaisseTransactionModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.CaisseQuery
import com.avenir.rangoapp.graphql.CaisseTransactionsQuery
import com.avenir.rangoapp.graphql.CaisseTransactionQuery
import com.avenir.rangoapp.graphql.CreateCaisseTransactionMutation
import com.avenir.rangoapp.graphql.DeleteCaisseTransactionMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLCaisseDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    
    suspend fun getCaisse(
        storeId: String? = null,
        currency: String? = null,
        period: String? = null
    ): Flow<BaseResponse<CaisseModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    CaisseQuery(
                        storeId = Optional.presentIfNotNull(storeId),
                        currency = Optional.presentIfNotNull(currency),
                        period = Optional.presentIfNotNull(period)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val caisse = response.data?.caisse
                if (caisse != null) {
                    val caisseModel = CaisseModel(
                        currentBalance = caisse.currentBalance.toDouble(),
                        inAmount = caisse.`in`.toDouble(),
                        outAmount = caisse.`out`.toDouble(),
                        currency = caisse.currency,
                        storeId = caisse.storeId,
                        store = caisse.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        }
                    )
                    emit(BaseResponse.Success(caisseModel))
                } else {
                    emit(BaseResponse.Error("No caisse data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLCaisseDataSource", "GetCaisse error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun getCaisseTransactions(
        storeId: String? = null,
        currency: String? = null,
        period: String? = null,
        limit: Int? = null
    ): Flow<BaseResponse<List<CaisseTransactionModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    CaisseTransactionsQuery(
                        storeId = Optional.presentIfNotNull(storeId),
                        currency = Optional.presentIfNotNull(currency),
                        period = Optional.presentIfNotNull(period),
                        limit = Optional.presentIfNotNull(limit)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val transactions = response.data?.caisseTransactions?.map { transaction ->
                    CaisseTransactionModel(
                        id = transaction.id,
                        amount = transaction.amount.toDouble(),
                        operation = transaction.operation,
                        description = transaction.description,
                        currency = transaction.currency,
                        storeId = transaction.storeId,
                        store = transaction.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        date = transaction.date,
                        createdAt = transaction.createdAt,
                        updatedAt = transaction.updatedAt
                    )
                } ?: emptyList()

                emit(BaseResponse.Success(transactions))
            } catch (ex: Exception) {
                Log.e("GraphQLCaisseDataSource", "GetCaisseTransactions error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun getCaisseTransaction(id: String): Flow<BaseResponse<CaisseTransactionModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(CaisseTransactionQuery(id)).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val transaction = response.data?.caisseTransaction
                if (transaction != null) {
                    val transactionModel = CaisseTransactionModel(
                        id = transaction.id,
                        amount = transaction.amount.toDouble(),
                        operation = transaction.operation,
                        description = transaction.description,
                        currency = transaction.currency,
                        storeId = transaction.storeId,
                        store = transaction.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        date = transaction.date,
                        createdAt = transaction.createdAt,
                        updatedAt = transaction.updatedAt
                    )
                    emit(BaseResponse.Success(transactionModel))
                } else {
                    emit(BaseResponse.Error("Transaction not found"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLCaisseDataSource", "GetCaisseTransaction error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createCaisseTransaction(
        amount: Double,
        operation: String, // "Entree" or "Sortie"
        description: String,
        currency: String, // "USD" or "CDF"
        storeId: String,
        date: String? = null
    ): Flow<BaseResponse<CaisseTransactionModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Validation
                if (operation !in listOf("Entree", "Sortie")) {
                    emit(BaseResponse.Error("L'opération doit être 'Entree' ou 'Sortie'"))
                    return@flow
                }

                if (amount <= 0) {
                    emit(BaseResponse.Error("Le montant doit être supérieur à 0"))
                    return@flow
                }

                if (currency !in listOf("USD", "CDF")) {
                    emit(BaseResponse.Error("La devise doit être 'USD' ou 'CDF'"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.CreateCaisseTransactionInput(
                    amount = amount,
                    operation = operation,
                    description = description,
                    currency = currency,
                    storeId = storeId,
                    date = Optional.presentIfNotNull(date)
                )

                val response = apolloClient.mutation(
                    CreateCaisseTransactionMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createCaisseTransaction
                if (data != null) {
                    val transactionModel = CaisseTransactionModel(
                        id = data.id,
                        amount = data.amount.toDouble(),
                        operation = data.operation,
                        description = data.description,
                        currency = data.currency,
                        storeId = data.storeId,
                        store = data.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        date = data.date,
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(transactionModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLCaisseDataSource", "CreateCaisseTransaction error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun deleteCaisseTransaction(id: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.mutation(
                    DeleteCaisseTransactionMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val deleted = response.data?.deleteCaisseTransaction ?: false
                emit(BaseResponse.Success(deleted))
            } catch (ex: Exception) {
                Log.e("GraphQLCaisseDataSource", "DeleteCaisseTransaction error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}
