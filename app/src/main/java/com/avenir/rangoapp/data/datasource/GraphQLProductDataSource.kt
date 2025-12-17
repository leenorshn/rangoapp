package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.ProductsQuery
import com.avenir.rangoapp.graphql.ProductQuery
import com.avenir.rangoapp.graphql.CreateProductMutation
import com.avenir.rangoapp.graphql.UpdateProductMutation
import com.avenir.rangoapp.graphql.DeleteProductMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLProductDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val companyDataStore: CompanyDataStore
) {
    
    suspend fun getAllProducts(storeId: String? = null): Flow<BaseResponse<List<ProductModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    ProductsQuery(
                        storeId = Optional.presentIfNotNull(storeId)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val products = response.data?.products?.mapNotNull { product ->
                    ProductModel(
                        id = product.id,
                        name = product.name,
                        mark = product.mark,
                        priceVente = product.priceVente.toDouble(),
                        priceAchat = product.priceAchat.toDouble(),
                        stock = product.stock.toDouble(),
                        storeId = product.storeId,
                        store = product.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = product.createdAt,
                        updatedAt = product.updatedAt
                    )
                } ?: emptyList()

                emit(BaseResponse.Success(products))
            } catch (ex: Exception) {
                Log.e("GraphQLProductDataSource", "GetAllProducts error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun getProduct(id: String): Flow<BaseResponse<ProductModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(ProductQuery(id)).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val product = response.data?.product
                if (product != null) {
                    val productModel = ProductModel(
                        id = product.id,
                        name = product.name,
                        mark = product.mark,
                        priceVente = product.priceVente.toDouble(),
                        priceAchat = product.priceAchat.toDouble(),
                        stock = product.stock.toDouble(),
                        storeId = product.storeId,
                        store = product.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = product.createdAt,
                        updatedAt = product.updatedAt
                    )
                    emit(BaseResponse.Success(productModel))
                } else {
                    emit(BaseResponse.Error("Product not found"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLProductDataSource", "GetProduct error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createProduct(
        name: String,
        mark: String,
        priceVente: Number,
        priceAchat: Number,
        stock: Number,
        storeId: String? = null
    ): Flow<BaseResponse<ProductModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Si storeId n'est pas fourni, utiliser celui du store actuel
                val currentStoreId = storeId ?: companyDataStore.readCompanyData()
                
                if (currentStoreId.isNullOrEmpty()) {
                    emit(BaseResponse.Error("Store ID is required"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.CreateProductInput(
                    name = name,
                    mark = mark,
                    priceVente = priceVente.toDouble(),
                    priceAchat = priceAchat.toDouble(),
                    stock = stock.toDouble(),
                    storeId = currentStoreId
                )

                val response = apolloClient.mutation(
                    CreateProductMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createProduct
                if (data != null) {
                    val productModel = ProductModel(
                        id = data.id,
                        name = data.name,
                        mark = data.mark,
                        priceVente = data.priceVente.toDouble(),
                        priceAchat = data.priceAchat.toDouble(),
                        stock = data.stock.toDouble(),
                        storeId = data.storeId,
                        store = data.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(productModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLProductDataSource", "CreateProduct error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun updateProduct(product: ProductModel): Flow<BaseResponse<ProductModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val input = com.avenir.rangoapp.graphql.type.UpdateProductInput(
                    name = Optional.presentIfNotNull(product.name.takeIf { it.isNotBlank() }),
                    mark = Optional.presentIfNotNull(product.mark.takeIf { it.isNotBlank() }),
                    priceVente = Optional.presentIfNotNull(product.priceVente.toDouble()),
                    priceAchat = Optional.presentIfNotNull(product.priceAchat.toDouble()),
                    stock = Optional.presentIfNotNull(product.stock.toDouble())
                )

                val response = apolloClient.mutation(
                    UpdateProductMutation(product.id, input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.updateProduct
                if (data != null) {
                    val productModel = ProductModel(
                        id = data.id,
                        name = data.name,
                        mark = data.mark,
                        priceVente = data.priceVente.toDouble(),
                        priceAchat = data.priceAchat.toDouble(),
                        stock = data.stock.toDouble(),
                        storeId = data.storeId,
                        store = data.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(productModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLProductDataSource", "UpdateProduct error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun deleteProduct(id: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.mutation(
                    DeleteProductMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val deleted = response.data?.deleteProduct ?: false
                if (deleted) {
                    emit(BaseResponse.Success(true))
                } else {
                    emit(BaseResponse.Error("Failed to delete product"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLProductDataSource", "DeleteProduct error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}
