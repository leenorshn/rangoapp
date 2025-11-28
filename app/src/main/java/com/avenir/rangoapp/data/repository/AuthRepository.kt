package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.models.GraphQLSession
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(phone: String, password: String): Flow<BaseResponse<GraphQLSession>>
    suspend fun register(
        password: String,
        name: String,
        phone: String
    ): Flow<BaseResponse<GraphQLSession>>
    suspend fun isUserLoggedIn(): Flow<BaseResponse<Boolean>>
    suspend fun logout(): Flow<BaseResponse<Boolean>>
    suspend fun getCurrentUser(): Flow<BaseResponse<UserModel>>
}