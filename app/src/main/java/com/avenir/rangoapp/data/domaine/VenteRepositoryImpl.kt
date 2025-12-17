package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLSaleDataSource
import com.avenir.rangoapp.data.models.SaleModel
import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.repository.VenteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenteRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLSaleDataSource
):VenteRepository {
    override suspend fun createSale(
        basket: List<Triple<String, Double, Double>>,
        priceToPay: Double,
        pricePayed: Double,
        clientId: String,
        storeId: String?,
        currency: String,
        date: String?
    ): Flow<BaseResponse<SaleModel>> {
        return dataSource.createSale(basket, priceToPay, pricePayed, clientId, storeId, currency, date)
    }

    override suspend fun getSales(storeId: String?): Flow<BaseResponse<List<SaleModel>>> {
        return dataSource.getSales(storeId)
    }
    
    override suspend fun getSale(id: String): Flow<BaseResponse<SaleModel>> {
        return dataSource.getSale(id)
    }
    
    override suspend fun deleteSale(id: String): Flow<BaseResponse<Boolean>> {
        return dataSource.deleteSale(id)
    }
    
    override suspend fun createFactureFromSale(saleId: String): Flow<BaseResponse<FactureModel>> {
        return dataSource.createFactureFromSale(saleId)
    }
}