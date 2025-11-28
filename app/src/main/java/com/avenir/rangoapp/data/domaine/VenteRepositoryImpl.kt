package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.repository.VenteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VenteRepositoryImpl @Inject constructor(
):VenteRepository {
    override suspend fun createVente(
        products: List<String>,
        client: String,
        quantity: Int,
        price: Double,
        date: String,
        currency: String
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            // TODO: Implement with GraphQL
            emit(BaseResponse.Error("Not implemented"))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }

    override suspend fun getFactures(): Flow<BaseResponse<List<FactureModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            // TODO: Implement with GraphQL
            emit(BaseResponse.Error("Not implemented"))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }
}