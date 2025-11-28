package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<BaseResponse<List<UserModel>>>
    suspend fun createUser(
        name: String,
        phone: String,
        password: String,
        role: String,
        storeId: String? = null
    ): Flow<BaseResponse<UserModel>>
}