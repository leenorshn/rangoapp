package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ClientModel
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun getClients(storeId: String? = null): Flow<BaseResponse<List<ClientModel>>>
    suspend fun createClient(
        name: String,
        phone: String,
        storeId: String? = null
    ): Flow<BaseResponse<ClientModel>>
}

