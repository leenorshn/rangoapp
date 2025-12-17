package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.graphql.UsersQuery
import com.avenir.rangoapp.graphql.CreateUserMutation
import com.avenir.rangoapp.graphql.UpdateUserMutation
import com.avenir.rangoapp.graphql.DeleteUserMutation
import com.avenir.rangoapp.graphql.BlockUserMutation
import com.avenir.rangoapp.graphql.UnblockUserMutation
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
                    UserModel(
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
                    storeId = Optional.presentIfNotNull(storeId)
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
                        id = data.id,
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

    suspend fun updateUser(
        id: String,
        name: String? = null,
        phone: String? = null,
        role: String? = null,
        storeId: String? = null
    ): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val input = com.avenir.rangoapp.graphql.type.UpdateUserInput(
                    name = Optional.presentIfNotNull(name),
                    phone = Optional.presentIfNotNull(phone),
                    role = Optional.presentIfNotNull(role),
                    storeId = Optional.presentIfNotNull(storeId)
                )

                val response = apolloClient.mutation(
                    UpdateUserMutation(id, input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.updateUser
                if (data != null) {
                    val userModel = UserModel(
                        id = data.id,
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
                Log.e("GraphQLUserDataSource", "UpdateUser error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun deleteUser(id: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (id.isBlank()) {
                    emit(BaseResponse.Error("L'ID de l'utilisateur est requis"))
                    return@flow
                }

                val response = apolloClient.mutation(
                    DeleteUserMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val deleted = response.data?.deleteUser ?: false
                emit(BaseResponse.Success(deleted))
            } catch (ex: Exception) {
                Log.e("GraphQLUserDataSource", "DeleteUser error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun blockUser(id: String): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (id.isBlank()) {
                    emit(BaseResponse.Error("L'ID de l'utilisateur est requis"))
                    return@flow
                }

                val response = apolloClient.mutation(
                    BlockUserMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.blockUser
                if (data != null) {
                    val userModel = UserModel(
                        id = data.id,
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
                Log.e("GraphQLUserDataSource", "BlockUser error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun unblockUser(id: String): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (id.isBlank()) {
                    emit(BaseResponse.Error("L'ID de l'utilisateur est requis"))
                    return@flow
                }

                val response = apolloClient.mutation(
                    UnblockUserMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.unblockUser
                if (data != null) {
                    val userModel = UserModel(
                        id = data.id,
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
                Log.e("GraphQLUserDataSource", "UnblockUser error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}


