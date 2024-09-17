package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.VenteDataSource
import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.repository.VenteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VenteRepositoryImpl @Inject constructor(
    private val dataSource: VenteDataSource
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
            val resp=dataSource.createFacture(products=products,
                price = price, currency = currency, client = client, date = date, quantity = quantity,)
            emit(BaseResponse.Success(resp))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }

    override suspend fun getFactures(): Flow<BaseResponse<List<FactureModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            val resp=dataSource.getFactures()
            emit(BaseResponse.Success(resp))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }
}