package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.graphql.UsersQuery
import com.avenir.rangoapp.graphql.CreateUserMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLUserDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    
    suspend fun getUsers(): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(UsersQuery()).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val users = response.data?.users?.mapNotNull { user ->
                    if (user != null) {
                        UserModel(
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
                    } else {
                        null
                    }
                } ?: emptyList()

                emit(BaseResponse.Success(users))
            } catch (ex: Exception) {
                Log.e("GraphQLUserDataSource", "GetUsers error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createUser(
        name: String,
        phone: String,
        password: String,
        role: String,
        storeId: String? = null
    ): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val input = com.avenir.rangoapp.graphql.type.CreateUserInput(
                    name = name,
                    phone = phone,
                    password = password,
                    role = role,
                    storeId = com.apollographql.apollo3.api.Optional.presentIfNotNull(storeId)
                )

                val response = apolloClient.mutation(
                    CreateUserMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createUser
                if (data != null) {
                    val userModel = UserModel(
                        uid = data.uid,
                        name = data.name,
                        phone = data.phone,
                        role = data.role,
                        isBlocked = data.isBlocked,
                        companyId = data.companyId,
                        storeIds = data.storeIds,
                        assignedStoreId = data.assignedStoreId,
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(userModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLUserDataSource", "CreateUser error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}

