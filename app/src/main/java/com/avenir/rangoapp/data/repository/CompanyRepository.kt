package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CompanyModel
import io.appwrite.models.Document
import kotlinx.coroutines.flow.Flow


interface CompanyRepository{
    suspend fun createCompany(
        name:String,
        address:String,
        phone:String,
        email:String,
    ): Flow<BaseResponse<Document<Map<String, Any>>>>
    suspend fun getCompany(): Flow<BaseResponse<CompanyModel>>

}