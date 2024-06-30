package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.AuthDataSource
import com.avenir.rangoapp.data.repository.AuthRepository
import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(phone: String, password: String): Flow<BaseResponse<Session>> {
        return authDataSource.onLogin(phone, password)
    }

    override suspend fun isUserLoggedIn(): Flow<BaseResponse<Boolean>> {
        return authDataSource.isUserLoggedIn()

    }

    override suspend fun logout() {
        return authDataSource.onLogout()
    }

    override suspend fun createUser(
        username: String,
        password: String,
    ): Flow<BaseResponse<User<Map<String, Any>>>> {
        return authDataSource.onRegister(
            username,
            password,
        )
    }




}