package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(phone: String, password: String): Flow<BaseResponse<Session>>
    suspend fun createUser(name: String, phone: String, password: String): Flow<BaseResponse<User<Map<String, Any>>>>
    suspend fun isUserLoggedIn():Flow<BaseResponse<Boolean>>
    suspend fun logout()
    suspend fun createAccount(
        email: String,
        password: String,
        name: String,
        phone: String,
        role: String,
        rccm: String,
        idNat: String,
        idCommerce: String,
        logo: String,
        address: String,
        description: String,
        type: String,
        city: String,
    ):Flow<BaseResponse<User<Map<String, Any>>>>
}