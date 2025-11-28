package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.models.FactureProductModel
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.FacturesQuery
import com.avenir.rangoapp.graphql.CreateFactureMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLFactureDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val companyDataStore: CompanyDataStore
) {
    
    suspend fun getFactures(storeId: String? = null): Flow<BaseResponse<List<FactureModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    FacturesQuery(
                        com.apollographql.apollo3.api.Optional.presentIfNotNull(storeId)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val factures = response.data?.factures?.map { facture ->
                    FactureModel(
                        id = facture.id,
                        factureNumber = facture.factureNumber,
                        products = facture.products.map { factureProduct ->
                            run {
                                val product = factureProduct.product
                                FactureProductModel(
                                    productId = factureProduct.productId,
                                    product = ProductModel(
                                        id = product.id,
                                        name = product.name,
                                        mark = product.mark,
                                        priceVente = product.priceVente,
                                        priceAchat = 0.0, // Not available in query
                                        stock = 0.0, // Not available in query
                                        storeId = facture.storeId,
                                        store = facture.store?.let { store ->
                                            StoreInfo(
                                                id = store.id,
                                                name = store.name,
                                                address = store.address
                                            )
                                        }
                                    ),
                                    quantity = factureProduct.quantity,
                                    price = factureProduct.price
                                )
                            }
                        },
                        quantity = facture.quantity,
                        date = facture.date,
                        price = facture.price,
                        currency = facture.currency,
                        client = facture.client?.let { client ->
                            ClientModel(
                                id = client.id,
                                name = client.name,
                                phone = client.phone
                            )
                        } ?: ClientModel("", "", ""),
                        storeId = facture.storeId,
                        store = facture.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address
                            )
                        },
                        createdAt = facture.createdAt,
                        updatedAt = facture.updatedAt
                    )
                } ?: emptyList()

                emit(BaseResponse.Success(factures))
            } catch (ex: Exception) {
                Log.e("GraphQLFactureDataSource", "GetFactures error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createFacture(
        products: List<Pair<String, Pair<Int, Double>>>, // List of (productId, (quantity, price))
        clientId: String,
        quantity: Int,
        price: Double,
        date: String,
        currency: String,
        storeId: String? = null
    ): Flow<BaseResponse<FactureModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Si storeId n'est pas fourni, utiliser celui du store actuel
                val currentStoreId = storeId ?: companyDataStore.readCompanyData()
                
                if (currentStoreId.isNullOrEmpty()) {
                    emit(BaseResponse.Error("Store ID is required"))
                    return@flow
                }

                val factureProducts = products.map { (productId, quantityPrice) ->
                    com.avenir.rangoapp.graphql.type.FactureProductInput(
                        productId = productId,
                        quantity = quantityPrice.first,
                        price = quantityPrice.second
                    )
                }

                val input = com.avenir.rangoapp.graphql.type.CreateFactureInput(
                    products = factureProducts,
                    clientId = clientId,
                    storeId = currentStoreId,
                    quantity = quantity,
                    price = price,
                    currency = currency,
                    date = date
                )

                val response = apolloClient.mutation(
                    CreateFactureMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createFacture
                if (data != null) {
                    val factureModel = FactureModel(
                        id = data.id,
                        factureNumber = data.factureNumber,
                        products = data.products.map { factureProduct ->
                            run {
                                val product = factureProduct.product
                                FactureProductModel(
                                    productId = factureProduct.productId,
                                    product = ProductModel(
                                        id = product.id,
                                        name = product.name,
                                        mark = product.mark,
                                        priceVente = 0.0, // Not available in mutation response
                                        priceAchat = 0.0,
                                        stock = 0.0,
                                        storeId = data.storeId
                                    ),
                                    quantity = factureProduct.quantity,
                                    price = factureProduct.price
                                )
                            }
                        },
                        quantity = data.quantity,
                        date = data.date,
                        price = data.price,
                        currency = data.currency,
                        client = data.client?.let { client ->
                            ClientModel(
                                id = client.id,
                                name = client.name,
                                phone = client.phone
                            )
                        } ?: ClientModel("", "", ""),
                        storeId = data.storeId,
                        store = data.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = null
                    )
                    emit(BaseResponse.Success(factureModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLFactureDataSource", "CreateFacture error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}

