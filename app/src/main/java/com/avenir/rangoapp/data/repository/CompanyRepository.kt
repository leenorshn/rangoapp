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
        description:String?,
        type:String?,
        rccm:String?,
        idNat:String?,
        idCommerce:String?,
        logo:String?,
        email:String?,
    ): Flow<BaseResponse<Document<Map<String, Any>>>>
    suspend fun getCompany(documentId: String): Flow<BaseResponse<CompanyModel>>

}