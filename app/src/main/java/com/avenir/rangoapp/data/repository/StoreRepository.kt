package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.StoreModel
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun getStores(): Flow<BaseResponse<List<StoreModel>>>
    suspend fun createStore(
        name: String,
        address: String,
        phone: String,
        companyId: String
    ): Flow<BaseResponse<StoreModel>>
}




