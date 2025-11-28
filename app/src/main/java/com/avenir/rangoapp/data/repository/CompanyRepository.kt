package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CompanyModel
import kotlinx.coroutines.flow.Flow


interface CompanyRepository{
    suspend fun createCompany(
        name: String,
        address: String,
        phone: String,
        email: String? = null,
        description: String,
        type: String,
        logo: String? = null,
        rccm: String? = null,
        idNat: String? = null,
        idCommerce: String? = null
    ): Flow<BaseResponse<CompanyModel>>
    suspend fun getCompany(): Flow<BaseResponse<CompanyModel>>

}