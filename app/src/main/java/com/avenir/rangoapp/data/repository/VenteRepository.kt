package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.SaleModel
import com.avenir.rangoapp.data.models.FactureModel
import kotlinx.coroutines.flow.Flow

interface VenteRepository {
    suspend fun createSale(
        basket: List<Triple<String, Double, Double>>, // List of (productId, quantity, price)
        priceToPay: Double,
        pricePayed: Double,
        clientId: String,
        storeId: String? = null,
        currency: String,
        date: String? = null
    ): Flow<BaseResponse<SaleModel>>

    suspend fun getSales(storeId: String? = null): Flow<BaseResponse<List<SaleModel>>>
    
    suspend fun getSale(id: String): Flow<BaseResponse<SaleModel>>
    
    suspend fun deleteSale(id: String): Flow<BaseResponse<Boolean>>
    
    suspend fun createFactureFromSale(saleId: String): Flow<BaseResponse<FactureModel>>
}