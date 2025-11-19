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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLAuthDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val tokenManager: TokenManager
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
                    if (graphQLUser != null) {
                        val userModel = UserModel(
                            uid = graphQLUser.uid,
                            name = graphQLUser.name,
                            phone = graphQLUser.phone,
                            role = graphQLUser.role,
                            isBlocked = graphQLUser.isBlocked
                        )
                        val session = GraphQLSession(
                            token = data.token,
                            user = userModel
                        )
                        emit(BaseResponse.Success(session))
                    } else {
                        emit(BaseResponse.Error("User data is null"))
                    }
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
        email: String,
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
        companyEmail: String? = null,
        companyLogo: String? = null,
        companyRccm: String? = null,
        companyIdNat: String? = null,
        companyIdCommerce: String? = null
    ): Flow<BaseResponse<GraphQLSession>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val registerInput = com.avenir.rangoapp.graphql.type.RegisterInput(
                    email = email,
                    password = password,
                    name = name,
                    phone = phone,
                    companyName = companyName,
                    companyAddress = companyAddress,
                    companyPhone = companyPhone,
                    companyDescription = companyDescription,
                    companyType = companyType,
                    companyEmail = com.apollographql.apollo3.api.Optional.presentIfNotNull(companyEmail),
                    companyLogo = com.apollographql.apollo3.api.Optional.presentIfNotNull(companyLogo),
                    companyRccm = com.apollographql.apollo3.api.Optional.presentIfNotNull(companyRccm),
                    companyIdNat = com.apollographql.apollo3.api.Optional.presentIfNotNull(companyIdNat),
                    companyIdCommerce = com.apollographql.apollo3.api.Optional.presentIfNotNull(companyIdCommerce),
                    storeName = storeName,
                    storeAddress = storeAddress,
                    storePhone = storePhone
                )
                
                val response = apolloClient.mutation(
                    RegisterMutation(registerInput)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.register
                if (data != null) {
                    // Save token
                    tokenManager.saveToken(data.token)
                    
                    // Convert GraphQL user to UserModel
                    val graphQLUser = data.user
                    if (graphQLUser != null) {
                        val userModel = UserModel(
                            uid = graphQLUser.uid,
                            name = graphQLUser.name,
                            phone = graphQLUser.phone,
                            role = graphQLUser.role,
                            isBlocked = graphQLUser.isBlocked
                        )
                        val session = GraphQLSession(
                            token = data.token,
                            user = userModel
                        )
                        emit(BaseResponse.Success(session))
                    } else {
                        emit(BaseResponse.Error("User data is null"))
                    }
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
                        uid = user.uid,
                        name = user.name,
                        phone = user.phone,
                        role = user.role,
                        isBlocked = user.isBlocked
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

                // Clear token regardless of response
                tokenManager.clearToken()
                emit(BaseResponse.Success(true))
            } catch (ex: Exception) {
                // Clear token even if request fails
                tokenManager.clearToken()
                Log.e("GraphQLAuthDataSource", "Logout error: ${ex.message}", ex)
                emit(BaseResponse.Success(true))
            }
        }
    }
}

