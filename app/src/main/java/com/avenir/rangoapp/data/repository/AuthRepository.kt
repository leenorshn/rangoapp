package com.avenir.rangoapp.data.repository

import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(phone: String, password: String): Flow<Session>
    suspend fun createUser(name: String, phone: String, password: String): Flow<User<Map<String, Any>>>
    suspend fun isUserLoggedIn():Flow<Boolean>

}