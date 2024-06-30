package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CompanyModel
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
    ): Flow<BaseResponse<Boolean>>
    suspend fun getCompany(): Flow<BaseResponse<CompanyModel>>

}