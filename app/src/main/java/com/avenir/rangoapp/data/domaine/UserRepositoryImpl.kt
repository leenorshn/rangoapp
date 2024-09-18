package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.CompanyDataStore
import com.avenir.rangoapp.data.datasource.UserDataSource
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,

):UserRepository {
    override suspend fun getUsers(): Flow<BaseResponse<List<UserModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            val t=dataSource.getUsers()
            emit(BaseResponse.Success(t))
        }.catch {
            emit(BaseResponse.Error("${it.message}"))
        }
    }

    override suspend fun createUser(): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
           //val t=dataSource.createUser()
        }
    }
}