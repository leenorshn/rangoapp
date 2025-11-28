package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CompanyModel
import kotlinx.coroutines.flow.Flow


interface CompanyRepository{
    suspend fun createCompany(
        name:String,
        address:String,
        phone:String,
        email:String,
    ): Flow<BaseResponse<CompanyModel>>
    suspend fun getCompany(): Flow<BaseResponse<CompanyModel>>

}