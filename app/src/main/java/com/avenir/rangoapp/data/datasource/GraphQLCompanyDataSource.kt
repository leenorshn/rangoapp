package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.CompanyQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLCompanyDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    
    suspend fun getCompany(): Flow<BaseResponse<CompanyModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(CompanyQuery()).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val company = response.data?.company
                if (company != null) {
                    val companyModel = CompanyModel(
                        id = company.id,
                        name = company.name,
                        address = company.address,
                        phone = company.phone,
                        description = company.description,
                        type = company.type,
                        logo = company.logo,
                        rccm = company.rccm,
                        idNat = company.idNat,
                        idCommerce = company.idCommerce,
                        stores = company.stores?.mapNotNull { store ->
                            if (store != null) {
                                StoreInfo(
                                    id = store.id,
                                    name = store.name,
                                    address = store.address,
                                    phone = store.phone
                                )
                            } else {
                                null
                            }
                        },
                        createdAt = company.createdAt,
                        updatedAt = company.updatedAt
                    )
                    emit(BaseResponse.Success(companyModel))
                } else {
                    emit(BaseResponse.Error("Company not found"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLCompanyDataSource", "GetCompany error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}

