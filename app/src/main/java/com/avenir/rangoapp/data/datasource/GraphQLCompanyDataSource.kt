package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.CompanyQuery
import com.avenir.rangoapp.graphql.CreateCompanyMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLCompanyDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    
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
    ): Flow<BaseResponse<CompanyModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Validation des champs requis
                if (name.isBlank() || address.isBlank() || phone.isBlank() || description.isBlank() || type.isBlank()) {
                    emit(BaseResponse.Error("Les champs nom, adresse, téléphone, description et type sont requis"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.CreateCompanyInput(
                    name = name.trim(),
                    address = address.trim(),
                    phone = phone.trim(),
                    description = description.trim(),
                    type = type.trim(),
                    email = Optional.presentIfNotNull(email?.trim()),
                    logo = Optional.presentIfNotNull(logo?.trim()),
                    rccm = Optional.presentIfNotNull(rccm?.trim()),
                    idNat = Optional.presentIfNotNull(idNat?.trim()),
                    idCommerce = Optional.presentIfNotNull(idCommerce?.trim())
                )

                val response = apolloClient.mutation(
                    CreateCompanyMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createCompany
                if (data != null) {
                    val companyModel = CompanyModel(
                        id = data.id,
                        name = data.name,
                        address = data.address,
                        phone = data.phone,
                        email = data.email,
                        description = data.description,
                        type = data.type,
                        logo = data.logo,
                        rccm = data.rccm,
                        idNat = data.idNat,
                        idCommerce = data.idCommerce,
                        stores = data.stores?.map { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(companyModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLCompanyDataSource", "CreateCompany error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
    
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

                val data = response.data?.company
                if (data != null) {
                    val companyModel = CompanyModel(
                        id = data.id,
                        name = data.name,
                        address = data.address,
                        phone = data.phone,
                        email = data.email,
                        description = data.description,
                        type = data.type,
                        logo = data.logo,
                        rccm = data.rccm,
                        idNat = data.idNat,
                        idCommerce = data.idCommerce,
                        stores = data.stores?.map { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(companyModel))
                } else {
                    emit(BaseResponse.Error("No company data"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLCompanyDataSource", "GetCompany error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}



