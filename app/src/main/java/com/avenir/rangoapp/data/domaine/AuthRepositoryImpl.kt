package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.AuthDataSource
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.repository.AuthRepository
import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun logout() :Flow<BaseResponse<Boolean>>{
        return flow {
            emit(BaseResponse.Loading)
            try {
                authDataSource.onLogout()
                emit(BaseResponse.Success(true))
            } catch (e: Exception) {
                emit(BaseResponse.Error("${e.message}"))
            }
        }
    }

    override suspend fun getCurrentUser(): Flow<BaseResponse<UserModel>> {
        return  authDataSource.getCurrentUser()
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