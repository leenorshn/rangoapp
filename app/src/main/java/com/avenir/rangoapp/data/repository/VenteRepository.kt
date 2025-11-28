package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.FactureModel
import kotlinx.coroutines.flow.Flow

interface VenteRepository {
    suspend fun createVente(
        products: List<Pair<String, Pair<Int, Double>>>, // List of (productId, (quantity, price))
        clientId: String,
        quantity: Int,
        price: Double,
        date: String,
        currency: String
    ): Flow<BaseResponse<FactureModel>>

    suspend fun getFactures(): Flow<BaseResponse<List<FactureModel>>>
}