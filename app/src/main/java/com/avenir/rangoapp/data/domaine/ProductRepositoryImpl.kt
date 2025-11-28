package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLProductDataSource
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLProductDataSource
):ProductRepository {
    override suspend fun createProduct(
        name: String,
        mark: String,
        priceVente: Number,
        priceAchat: Number,
        stock: Number
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            dataSource.createProduct(name, mark, priceVente, priceAchat, stock).collect { response ->
                when (response) {
                    is BaseResponse.Success -> emit(BaseResponse.Success(true))
                    is BaseResponse.Error -> emit(BaseResponse.Error(response.error))
                    is BaseResponse.Loading -> emit(BaseResponse.Loading)
                }
            }
        }.catch {
            emit(BaseResponse.Error(error = "${it.message}"))
        }
    }

    override suspend fun updateProduct(product: ProductModel): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            dataSource.updateProduct(product).collect { response ->
                when (response) {
                    is BaseResponse.Success -> emit(BaseResponse.Success(true))
                    is BaseResponse.Error -> emit(BaseResponse.Error(response.error))
                    is BaseResponse.Loading -> emit(BaseResponse.Loading)
                }
            }
        }.catch {
            emit(BaseResponse.Error(error = "${it.message}"))
        }
    }

    override suspend fun deleteProduct(id: String): Flow<BaseResponse<Boolean>> {
        return dataSource.deleteProduct(id)
    }

    override suspend fun getAllProducts(): Flow<BaseResponse<List<ProductModel>>> {
        return dataSource.getAllProducts()
    }

    override suspend fun getProduct(id: String): Flow<BaseResponse<ProductModel>> {
        return dataSource.getProduct(id)
    }
}