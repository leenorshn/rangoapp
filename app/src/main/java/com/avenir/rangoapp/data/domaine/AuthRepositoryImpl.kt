package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.UserDataSource
import com.avenir.rangoapp.data.repository.AuthRepository
import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): AuthRepository {
    override suspend fun login(phone: String, password: String): Flow<BaseResponse<Session>> {
        return userDataSource.onLogin(phone,password)
    }

    override suspend fun createUser(name: String, phone: String, password: String): Flow<BaseResponse<User<Map<String, Any>>>> {
        return userDataSource.onRegister(phone,password);

    }

    override suspend fun isUserLoggedIn(): Flow<BaseResponse<Boolean>> {
        return userDataSource.isUserLoggedIn()

    }

    override suspend fun logout() {
        return userDataSource.onLogout()
    }
}