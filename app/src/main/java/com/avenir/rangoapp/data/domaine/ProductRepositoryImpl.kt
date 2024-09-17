package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.ProductDataSource
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource
):ProductRepository {
    override suspend fun createProduct(
        name: String,
        mark: String,
        priceVente: Double,
        priceAchat: Double,
        stock: Int
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            val list=dataSource.createProduct(name=name, mark = mark,priceVente=priceVente,priceAchat=priceAchat,stock=stock,)
            emit(BaseResponse.Success(list))
        }.catch {
            emit(BaseResponse.Error(error = "${it.message}"))
        }
    }

    override suspend fun updateProduct(product: ProductModel): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            val ops=dataSource.updateProduct(product=product)
            emit(BaseResponse.Success(ops))
        }.catch {
            emit(BaseResponse.Error(error = "${it.message}"))
        }
    }

    override suspend fun deleteProduct(id: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            val ops=dataSource.deleteProduct(id=id)
            emit(BaseResponse.Success(ops))
        }.catch {
            emit(BaseResponse.Error(error = "${it.message}"))
        }
    }

    override suspend fun getAllProducts(): Flow<BaseResponse<List<ProductModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            val ops=dataSource.getAllProducts()
            emit(BaseResponse.Success(ops))
        }.catch {
            emit(BaseResponse.Error(error = "${it.message}"))
        }
    }

    override suspend fun getProduct(id: String): Flow<BaseResponse<ProductModel>> {
        return flow {
            emit(BaseResponse.Loading)
            val ops=dataSource.getProduct(id=id)
            emit(BaseResponse.Success(ops))
        }.catch {
            emit(BaseResponse.Error(error="${it.message}"))
        }
    }
}