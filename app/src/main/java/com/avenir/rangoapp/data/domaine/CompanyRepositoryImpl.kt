package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.CompanyDataSource
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.repository.CompanyRepository
import io.appwrite.models.Document
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyDataSource: CompanyDataSource
) : CompanyRepository {
    override suspend fun createCompany(
        name: String,
        address: String,
        phone: String,
        description: String?,
        type: String?,
        rccm: String?,
        idNat: String?,
        idCommerce: String?,
        logo: String?,
        email: String?
    ): Flow<BaseResponse<Document<Map<String, Any>>>> {


        return flow {
            emit(BaseResponse.Loading)
            try {
                val docs = companyDataSource.createCompany(
                    name,
                    address,
                    phone,
                    description = description.orEmpty(),
                    type = type.orEmpty(),
                    rccm = rccm.orEmpty(),
                    idNat = idNat.orEmpty(),
                    idCommerce = idCommerce.orEmpty(),
                    logo = logo.orEmpty(),
                    email = email.orEmpty()
                );
                emit(BaseResponse.Success(docs))
            } catch (e: Exception) {
                emit(BaseResponse.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getCompany(documentId: String): Flow<BaseResponse<CompanyModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val doc = companyDataSource.getCompany(documentId)
                emit(BaseResponse.Success(doc))
            } catch (e: Exception) {
                emit(BaseResponse.Error(e.message.toString()))
            }
        }
    }
}