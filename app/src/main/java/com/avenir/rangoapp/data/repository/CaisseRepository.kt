package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CaisseModel
import com.avenir.rangoapp.data.models.CaisseTransactionModel
import kotlinx.coroutines.flow.Flow

interface CaisseRepository {
    suspend fun getCaisse(
        storeId: String? = null,
        currency: String? = null,
        period: String? = null
    ): Flow<BaseResponse<CaisseModel>>
    
    suspend fun getCaisseTransactions(
        storeId: String? = null,
        currency: String? = null,
        period: String? = null,
        limit: Int? = null
    ): Flow<BaseResponse<List<CaisseTransactionModel>>>
    
    suspend fun createCaisseTransaction(
        amount: Double,
        operation: String, // "Entree" or "Sortie"
        description: String,
        currency: String, // "USD" or "CDF"
        storeId: String,
        date: String? = null
    ): Flow<BaseResponse<CaisseTransactionModel>>
    
    suspend fun deleteCaisseTransaction(id: String): Flow<BaseResponse<Boolean>>
}
