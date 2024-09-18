package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers():Flow<BaseResponse<List<UserModel>>>
    suspend fun createUser():Flow<BaseResponse<Boolean>>

}