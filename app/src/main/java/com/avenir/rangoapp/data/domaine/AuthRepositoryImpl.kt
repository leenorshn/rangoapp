package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.data.datasource.UserDataSource
import com.avenir.rangoapp.data.repository.AuthRepository
import io.appwrite.models.Session
import io.appwrite.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): AuthRepository {
    override suspend fun login(phone: String, password: String): Flow<Session> {
        return flow {
            userDataSource.onLogin(phone,password)
        }
    }

    override suspend fun createUser(name: String, phone: String, password: String): Flow<User<Map<String, Any>>> {
        return  flow{
            userDataSource.onRegister(phone,password);
        }
    }

    override suspend fun isUserLoggedIn(): Flow<Boolean> {
        return  flow {
            userDataSource.isUserLoggedIn()
        }
    }
}