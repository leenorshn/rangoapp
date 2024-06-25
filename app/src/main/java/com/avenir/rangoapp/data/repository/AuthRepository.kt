package com.avenir.rangoapp.data.repository

import io.appwrite.models.Session
import io.appwrite.models.User

interface AuthRepository {
    suspend fun login(phone: String, password: String): Session
    suspend fun createUser(name: String, phone: String, password: String): User<Map<String, Any>>

}