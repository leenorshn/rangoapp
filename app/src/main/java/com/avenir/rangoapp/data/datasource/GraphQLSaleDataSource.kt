package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.models.SaleModel
import com.avenir.rangoapp.data.models.SaleProductModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.data.models.UserInfo
import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.graphql.SalesQuery
import com.avenir.rangoapp.graphql.SaleQuery
import com.avenir.rangoapp.graphql.CreateSaleMutation
import com.avenir.rangoapp.graphql.DeleteSaleMutation
import com.avenir.rangoapp.graphql.CreateFactureFromSaleMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLSaleDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val companyDataStore: CompanyDataStore
) {
    
    suspend fun getSales(storeId: String? = null): Flow<BaseResponse<List<SaleModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    SalesQuery(
                        storeId = Optional.presentIfNotNull(storeId)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val sales = response.data?.sales?.mapNotNull { sale ->
                    SaleModel(
                        id = sale.id,
                        basket = sale.basket.map { item ->
                            SaleProductModel(
                                productId = item.productId,
                                product = ProductModel(
                                    id = item.product.id,
                                    name = item.product.name,
                                    mark = "",
                                    priceVente = item.product.priceVente,
                                    priceAchat = 0.0,
                                    stock = 0.0,
                                    storeId = ""
                                ),
                                quantity = item.quantity,
                                price = item.price
                            )
                        },
                        priceToPay = sale.priceToPay,
                        pricePayed = sale.pricePayed,
                        change = sale.change,
                        currency = sale.currency,
                        client = ClientModel(
                            id = sale.client.id,
                            name = sale.client.name,
                            phone = sale.client.phone,
                            storeId = ""
                        ),
                        operator = sale.operator?.let { op ->
                            UserInfo(
                                id = op.id,
                                name = op.name
                            )
                        },
                        storeId = sale.store?.id ?: "",
                        store = sale.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = null,
                                phone = null
                            )
                        },
                        date = sale.date,
                        createdAt = sale.createdAt,
                        updatedAt = sale.updatedAt
                    )
                } ?: emptyList()

                emit(BaseResponse.Success(sales))
            } catch (ex: Exception) {
                Log.e("GraphQLSaleDataSource", "GetSales error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun getSale(id: String): Flow<BaseResponse<SaleModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(SaleQuery(id)).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val sale = response.data?.sale
                if (sale != null) {
                    val saleModel = SaleModel(
                        id = sale.id,
                        basket = sale.basket.map { item ->
                            SaleProductModel(
                                productId = item.productId,
                                product = ProductModel(
                                    id = item.product.id,
                                    name = item.product.name,
                                    mark = item.product.mark ?: "",
                                    priceVente = item.product.priceVente,
                                    priceAchat = 0.0,
                                    stock = 0.0,
                                    storeId = ""
                                ),
                                quantity = item.quantity,
                                price = item.price
                            )
                        },
                        priceToPay = sale.priceToPay,
                        pricePayed = sale.pricePayed,
                        change = sale.change,
                        currency = sale.currency,
                        client = ClientModel(
                            id = sale.client.id,
                            name = sale.client.name,
                            phone = sale.client.phone,
                            storeId = ""
                        ),
                        operator = sale.operator?.let { op ->
                            UserInfo(
                                id = op.id,
                                name = op.name
                            )
                        },
                        storeId = sale.store?.id ?: "",
                        store = sale.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = null,
                                phone = null
                            )
                        },
                        date = sale.date,
                        createdAt = sale.createdAt,
                        updatedAt = sale.updatedAt
                    )
                    emit(BaseResponse.Success(saleModel))
                } else {
                    emit(BaseResponse.Error("Sale not found"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLSaleDataSource", "GetSale error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createSale(
        basket: List<Triple<String, Double, Double>>, // List of (productId, quantity, price)
        priceToPay: Double,
        pricePayed: Double,
        clientId: String,
        storeId: String? = null,
        currency: String,
        date: String? = null
    ): Flow<BaseResponse<SaleModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Validation
                if (basket.isEmpty()) {
                    emit(BaseResponse.Error("Le panier ne peut pas être vide"))
                    return@flow
                }

                if (priceToPay <= 0) {
                    emit(BaseResponse.Error("Le montant à payer doit être supérieur à 0"))
                    return@flow
                }

                // Si storeId n'est pas fourni, utiliser celui du store actuel
                val currentStoreId = storeId ?: companyDataStore.readCompanyData()
                
                if (currentStoreId.isNullOrEmpty()) {
                    emit(BaseResponse.Error("Store ID is required"))
                    return@flow
                }

                val saleProducts = basket.map { (productId, quantity, price) ->
                    com.avenir.rangoapp.graphql.type.SaleProductInput(
                        productId = productId,
                        quantity = quantity,
                        price = price
                    )
                }

                val input = com.avenir.rangoapp.graphql.type.CreateSaleInput(
                    basket = saleProducts,
                    priceToPay = priceToPay,
                    pricePayed = pricePayed,
                    clientId = clientId,
                    storeId = currentStoreId,
                    currency = currency,
                    date = Optional.presentIfNotNull(date)
                )

                val response = apolloClient.mutation(
                    CreateSaleMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createSale
                if (data != null) {
                    val saleModel = SaleModel(
                        id = data.id,
                        basket = data.basket.map { item ->
                            SaleProductModel(
                                productId = item.productId,
                                product = ProductModel(
                                    id = item.product.id,
                                    name = item.product.name,
                                    mark = "",
                                    priceVente = item.product.priceVente,
                                    priceAchat = 0.0,
                                    stock = 0.0,
                                    storeId = ""
                                ),
                                quantity = item.quantity,
                                price = item.price
                            )
                        },
                        priceToPay = data.priceToPay,
                        pricePayed = data.pricePayed,
                        change = data.change,
                        currency = data.currency,
                        client = ClientModel(
                            id = data.client.id,
                            name = data.client.name,
                            phone = data.client.phone,
                            storeId = ""
                        ),
                        operator = data.operator?.let { op ->
                            UserInfo(
                                id = op.id,
                                name = op.name
                            )
                        },
                        storeId = data.store?.id ?: "",
                        store = data.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = null,
                                phone = null
                            )
                        },
                        date = data.date,
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(saleModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLSaleDataSource", "CreateSale error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun deleteSale(id: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (id.isBlank()) {
                    emit(BaseResponse.Error("L'ID de la vente est requis"))
                    return@flow
                }

                val response = apolloClient.mutation(
                    DeleteSaleMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val deleted = response.data?.deleteSale ?: false
                emit(BaseResponse.Success(deleted))
            } catch (ex: Exception) {
                Log.e("GraphQLSaleDataSource", "DeleteSale error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createFactureFromSale(saleId: String): Flow<BaseResponse<FactureModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (saleId.isBlank()) {
                    emit(BaseResponse.Error("L'ID de la vente est requis"))
                    return@flow
                }

                val response = apolloClient.mutation(
                    CreateFactureFromSaleMutation(saleId)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createFactureFromSale
                if (data != null) {
                    val factureModel = FactureModel(
                        id = data.id,
                        factureNumber = data.factureNumber,
                        products = data.products.map { product ->
                            com.avenir.rangoapp.data.models.FactureProductModel(
                                productId = product.productId,
                                product = ProductModel(
                                    id = product.product?.id ?: "",
                                    name = product.product?.name ?: "",
                                    mark = "",
                                    priceVente = 0.0,
                                    priceAchat = 0.0,
                                    stock = 0.0,
                                    storeId = ""
                                ),
                                quantity = product.quantity.toInt(),
                                price = product.price
                            )
                        },
                        quantity = 0,
                        date = data.date,
                        price = data.price,
                        currency = data.currency,
                        client = ClientModel(
                            id = data.client.id,
                            name = data.client.name,
                            phone = data.client.phone,
                            storeId = ""
                        ),
                        storeId = data.store?.id,
                        store = data.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = null
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(factureModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLSaleDataSource", "CreateFactureFromSale error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}


