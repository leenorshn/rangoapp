package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.data.datasource.UserDataSource
import com.avenir.rangoapp.data.repository.AuthRepository
import io.appwrite.models.Session
import io.appwrite.models.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): AuthRepository {
    override suspend fun login(phone: String, password: String): Session {
        return userDataSource.onLogin(phone,password)
    }

    override suspend fun createUser(name: String, phone: String, password: String): User<Map<String, Any>> {
        return  userDataSource.onRegister(phone,password);
    }
}