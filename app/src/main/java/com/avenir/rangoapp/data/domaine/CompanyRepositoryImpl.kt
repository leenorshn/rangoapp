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
        email: String
    ): Flow<BaseResponse<CompanyModel>> {
        // La création de company se fait lors de l'enregistrement
        // Pour l'instant, on retourne une erreur car updateCompany existe dans le schéma
        return dataSource.getCompany()
    }

    override suspend fun getCompany(): Flow<BaseResponse<CompanyModel>> {
        return dataSource.getCompany()
    }
}