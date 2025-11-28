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
        phone: String,
        companyName: String,
        companyAddress: String,
        companyPhone: String,
        companyDescription: String,
        companyType: String,
        storeName: String,
        storeAddress: String,
        storePhone: String,
        companyLogo: String?,
        companyRccm: String?,
        companyIdNat: String?,
        companyIdCommerce: String?
    ): Flow<BaseResponse<GraphQLSession>> {
        return graphQLAuthDataSource.register(
            password = password,
            name = name,
            phone = phone,
            companyName = companyName,
            companyAddress = companyAddress,
            companyPhone = companyPhone,
            companyDescription = companyDescription,
            companyType = companyType,
            storeName = storeName,
            storeAddress = storeAddress,
            storePhone = storePhone,
            companyLogo = companyLogo,
            companyRccm = companyRccm,
            companyIdNat = companyIdNat,
            companyIdCommerce = companyIdCommerce
        )
    }
}