package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLFactureDataSource
import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.repository.VenteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenteRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLFactureDataSource
):VenteRepository {
    override suspend fun createVente(
        products: List<Pair<String, Pair<Int, Double>>>, // List of (productId, (quantity, price))
        clientId: String,
        quantity: Int,
        price: Double,
        date: String,
        currency: String
    ): Flow<BaseResponse<FactureModel>> {
        return dataSource.createFacture(products, clientId, quantity, price, date, currency)
    }

    override suspend fun getFactures(): Flow<BaseResponse<List<FactureModel>>> {
        return dataSource.getFactures()
    }
}