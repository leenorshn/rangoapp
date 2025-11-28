package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLCompanyDataSource
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLCompanyDataSource
) : CompanyRepository {
    override suspend fun createCompany(
        name: String,
        address: String,
        phone: String,
        email: String?,
        description: String,
        type: String,
        logo: String?,
        rccm: String?,
        idNat: String?,
        idCommerce: String?
    ): Flow<BaseResponse<CompanyModel>> {
        return dataSource.createCompany(
            name = name,
            address = address,
            phone = phone,
            email = email,
            description = description,
            type = type,
            logo = logo,
            rccm = rccm,
            idNat = idNat,
            idCommerce = idCommerce
        )
    }

    override suspend fun getCompany(): Flow<BaseResponse<CompanyModel>> {
        return dataSource.getCompany()
    }
}