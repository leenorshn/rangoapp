package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ProviderModel
import kotlinx.coroutines.flow.Flow


interface ProviderRepository {
    suspend fun createProvider(
        name:String,
        phone:String,
        address:String
    ):Flow<BaseResponse<Boolean>>

    suspend fun getProviders():Flow<BaseResponse<List<ProviderModel>>>
}