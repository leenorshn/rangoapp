package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.RapportStoreQuery
import com.avenir.rangoapp.graphql.CreateRapportStoreMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLRapportStoreDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val companyDataStore: CompanyDataStore
) {
    
    suspend fun getRapportStore(storeId: String? = null): Flow<BaseResponse<List<RapportStoreModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    RapportStoreQuery(
                        com.apollographql.apollo3.api.Optional.presentIfNotNull(storeId)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val rapports = response.data?.rapportStore?.mapNotNull { rapport ->
                    if (rapport != null && rapport.product != null) {
                        val product = rapport.product
                        RapportStoreModel(
                            id = rapport.id,
                            type = rapport.type,
                            product = ProductModel(
                                id = product.id,
                                name = product.name,
                                mark = product.mark,
                                priceVente = product.priceVente,
                                priceAchat = 0.0, // Not available in query
                                stock = product.stock,
                                storeId = rapport.storeId,
                                store = rapport.store?.let { store ->
                                    StoreInfo(
                                        id = store.id,
                                        name = store.name,
                                        address = store.address
                                    )
                                }
                            ),
                            date = rapport.date,
                            quantity = rapport.quantity,
                            storeId = rapport.storeId,
                            store = rapport.store?.let { store ->
                                StoreInfo(
                                    id = store.id,
                                    name = store.name,
                                    address = store.address
                                )
                            },
                            createdAt = rapport.createdAt,
                            updatedAt = rapport.updatedAt
                        )
                    } else {
                        null
                    }
                } ?: emptyList()

                emit(BaseResponse.Success(rapports))
            } catch (ex: Exception) {
                Log.e("GraphQLRapportStoreDataSource", "GetRapportStore error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createRapport(
        productId: String,
        quantity: Number,
        type: String,
        storeId: String? = null
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Si storeId n'est pas fourni, utiliser celui du store actuel
                val currentStoreId = storeId ?: companyDataStore.readCompanyData()
                
                if (currentStoreId.isNullOrEmpty()) {
                    emit(BaseResponse.Error("Store ID is required"))
                    return@flow
                }

                // Format de date ISO 8601
                val date = java.time.Instant.now().toString()

                val input = com.avenir.rangoapp.graphql.type.CreateRapportStoreInput(
                    productId = productId,
                    storeId = currentStoreId,
                    quantity = quantity.toDouble(),
                    type = type,
                    date = date
                )

                val response = apolloClient.mutation(
                    CreateRapportStoreMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createRapportStore
                if (data != null) {
                    emit(BaseResponse.Success(true))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLRapportStoreDataSource", "CreateRapport error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}

