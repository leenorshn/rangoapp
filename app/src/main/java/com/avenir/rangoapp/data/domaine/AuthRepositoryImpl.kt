package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLAuthDataSource
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.models.GraphQLSession
import com.avenir.rangoapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val graphQLAuthDataSource: GraphQLAuthDataSource
) : AuthRepository {
    
    override suspend fun login(phone: String, password: String): Flow<BaseResponse<GraphQLSession>> {
        return graphQLAuthDataSource.login(phone, password)
    }

    override suspend fun isUserLoggedIn(): Flow<BaseResponse<Boolean>> {
        return graphQLAuthDataSource.isUserLoggedIn()
    }

    override suspend fun logout(): Flow<BaseResponse<Boolean>> {
        return graphQLAuthDataSource.logout()
    }

    override suspend fun getCurrentUser(): Flow<BaseResponse<UserModel>> {
        return graphQLAuthDataSource.getCurrentUser()
    }

    override suspend fun register(
        password: String,
        name: String,
        phone: String
    ): Flow<BaseResponse<GraphQLSession>> {
        return graphQLAuthDataSource.register(
            password = password,
            name = name,
            phone = phone
        )
    }
}