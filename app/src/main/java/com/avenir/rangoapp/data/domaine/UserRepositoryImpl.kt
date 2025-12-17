package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLUserDataSource
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLUserDataSource
):UserRepository {
    override suspend fun getUsers(): Flow<BaseResponse<List<UserModel>>> {
        return dataSource.getUsers()
    }

    override suspend fun createUser(
        name: String,
        phone: String,
        password: String,
        role: String,
        storeId: String?
    ): Flow<BaseResponse<UserModel>> {
        return dataSource.createUser(name, phone, password, role, storeId)
    }

    override suspend fun updateUser(
        id: String,
        name: String?,
        phone: String?,
        role: String?,
        storeId: String?
    ): Flow<BaseResponse<UserModel>> {
        return dataSource.updateUser(id, name, phone, role, storeId)
    }

    override suspend fun deleteUser(id: String): Flow<BaseResponse<Boolean>> {
        return dataSource.deleteUser(id)
    }

    override suspend fun blockUser(id: String): Flow<BaseResponse<UserModel>> {
        return dataSource.blockUser(id)
    }

    override suspend fun unblockUser(id: String): Flow<BaseResponse<UserModel>> {
        return dataSource.unblockUser(id)
    }
}