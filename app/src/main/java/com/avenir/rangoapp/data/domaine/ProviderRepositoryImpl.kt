package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ProviderModel
import com.avenir.rangoapp.data.repository.ProviderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProviderRepositoryImpl @Inject constructor(
):ProviderRepository {
    override suspend fun createProvider(
        name: String,
        phone: String,
        address: String
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            // TODO: Implement with GraphQL
            emit(BaseResponse.Error("Not implemented"))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }

    override suspend fun getProviders(): Flow<BaseResponse<List<ProviderModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            // TODO: Implement with GraphQL
            emit(BaseResponse.Error("Not implemented"))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }
}