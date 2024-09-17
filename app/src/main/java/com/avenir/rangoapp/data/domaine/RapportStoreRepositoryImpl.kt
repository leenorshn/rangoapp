package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.RapportStoreDataSource
import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.data.repository.RapportStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RapportStoreRepositoryImpl @Inject constructor(
    private val dataSource: RapportStoreDataSource
):RapportStoreRepository {
    override suspend fun createRapport(
        productId: String,
        productName: String,
        type: String
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            val res=dataSource.createRapport(
                productId=productId,
                productName=productName,
                type=type
            )

            emit(BaseResponse.Success(res))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }


    override suspend fun getRapportStore(): Flow<BaseResponse<List<RapportStoreModel>>> {
       return flow {
           emit(BaseResponse.Loading)
           val res=dataSource.getRapportStore()
           emit(BaseResponse.Success(res))
       }.catch {
           emit(BaseResponse.Error(it.message+""))
       }
    }
}