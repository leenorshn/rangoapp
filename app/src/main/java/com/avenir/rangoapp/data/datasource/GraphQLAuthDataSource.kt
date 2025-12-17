package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.TokenManager
import com.avenir.rangoapp.graphql.LoginMutation
import com.avenir.rangoapp.graphql.MeQuery
import com.avenir.rangoapp.graphql.RegisterMutation
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.models.GraphQLSession
import com.avenir.rangoapp.data.datasource.CompanyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GraphQLAuthDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val tokenManager: TokenManager,
    private val companyDataStore: CompanyDataStore
) {
    
    suspend fun login(phone: String, password: String): Flow<BaseResponse<GraphQLSession>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.mutation(
                    LoginMutation(phone, password)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.login
                if (data != null) {
                    // Save token
                    tokenManager.saveToken(data.token)
                    
                    // Convert GraphQL user to UserModel
                    val graphQLUser = data.user
                    val userModel = UserModel(
                        id = graphQLUser.id,
                        uid = graphQLUser.uid,
                        name = graphQLUser.name,
                        phone = graphQLUser.phone,
                        role = graphQLUser.role,
                        isBlocked = graphQLUser.isBlocked,
                        companyId = graphQLUser.companyId,
                        storeIds = graphQLUser.storeIds,
                        assignedStoreId = graphQLUser.assignedStoreId,
                        createdAt = graphQLUser.createdAt,
                        updatedAt = graphQLUser.updatedAt
                    )
                    
                    // Save storeId to CompanyDataStore (non-blocking)
                    val storeIdToSave = userModel.assignedStoreId
                        ?: userModel.storeIds?.firstOrNull()
                    if (storeIdToSave != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            companyDataStore.saveCompany(storeIdToSave)
                        }
                    }

                    val session = GraphQLSession(
                        token = data.token,
                        user = userModel
                    )
                    emit(BaseResponse.Success(session))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLAuthDataSource", "Login error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun register(
        password: String,
        name: String,
        phone: String
    ): Flow<BaseResponse<GraphQLSession>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val input = com.avenir.rangoapp.graphql.type.RegisterInput(
                    name = name.trim(),
                    phone = phone.trim(),
                    password = password
                )

                val response = apolloClient.mutation(
                    RegisterMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.register
                if (data != null) {
                    tokenManager.saveToken(data.token)
                    
                    val graphQLUser = data.user
                    val userModel = UserModel(
                        id = graphQLUser.id,
                        uid = graphQLUser.uid,
                        name = graphQLUser.name,
                        phone = graphQLUser.phone,
                        role = graphQLUser.role,
                        isBlocked = graphQLUser.isBlocked,
                        companyId = graphQLUser.companyId,
                        storeIds = graphQLUser.storeIds,
                        assignedStoreId = graphQLUser.assignedStoreId,
                        createdAt = graphQLUser.createdAt,
                        updatedAt = graphQLUser.updatedAt
                    )

                    val session = GraphQLSession(
                        token = data.token,
                        user = userModel
                    )
                    emit(BaseResponse.Success(session))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLAuthDataSource", "Register error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun getCurrentUser(): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(MeQuery()).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val user = response.data?.me
                if (user != null) {
                    val userModel = UserModel(
                        id = user.id,
                        uid = user.uid,
                        name = user.name,
                        phone = user.phone,
                        role = user.role,
                        isBlocked = user.isBlocked,
                        companyId = user.companyId,
                        storeIds = user.storeIds,
                        assignedStoreId = user.assignedStoreId,
                        createdAt = user.createdAt,
                        updatedAt = user.updatedAt
                    )
                    emit(BaseResponse.Success(userModel))
                } else {
                    emit(BaseResponse.Error("No user data"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLAuthDataSource", "GetCurrentUser error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun isUserLoggedIn(): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val hasToken = tokenManager.hasToken()
                if (hasToken) {
                    // Verify token is still valid by calling me
                    val response = apolloClient.query(MeQuery()).execute()
                    emit(BaseResponse.Success(!response.hasErrors()))
                } else {
                    emit(BaseResponse.Success(false))
                }
            } catch (e: Exception) {
                Log.e("GraphQLAuthDataSource", "isUserLoggedIn error: ${e.message}", e)
                emit(BaseResponse.Error("Error ${e.message}"))
            }
        }
    }

    suspend fun logout(): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.mutation(
                    com.avenir.rangoapp.graphql.LogoutMutation()
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                // Clear token locally
                tokenManager.clearToken()
                emit(BaseResponse.Success(true))
            } catch (ex: Exception) {
                Log.e("GraphQLAuthDataSource", "Logout error: ${ex.message}", ex)
                // Clear token locally even if request fails
                tokenManager.clearToken()
                emit(BaseResponse.Success(true))
            }
        }
    }
}
