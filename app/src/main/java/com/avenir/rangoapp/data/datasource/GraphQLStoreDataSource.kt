package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.StoreModel
import com.avenir.rangoapp.data.models.CompanyInfo
import com.avenir.rangoapp.graphql.StoresQuery
import com.avenir.rangoapp.graphql.CreateStoreMutation
import com.avenir.rangoapp.graphql.MeQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLStoreDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    
    suspend fun getStores(): Flow<BaseResponse<List<StoreModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(StoresQuery()).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val stores = response.data?.stores?.map { store ->
                    StoreModel(
                        id = store.id,
                        name = store.name,
                        address = store.address,
                        phone = store.phone,
                        companyId = store.companyId,
                        company = store.company?.let { company ->
                            CompanyInfo(
                                id = company.id,
                                name = company.name
                            )
                        },
                        createdAt = store.createdAt,
                        updatedAt = store.updatedAt
                    )
                } ?: emptyList()

                emit(BaseResponse.Success(stores))
            } catch (ex: Exception) {
                Log.e("GraphQLStoreDataSource", "GetStores error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createStore(
        name: String,
        address: String,
        phone: String,
        companyId: String
    ): Flow<BaseResponse<StoreModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Validate required fields
                if (name.isBlank() || address.isBlank() || phone.isBlank() || companyId.isBlank()) {
                    emit(BaseResponse.Error("Tous les champs requis doivent être remplis"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.CreateStoreInput(
                    name = name.trim(),
                    address = address.trim(),
                    phone = phone.trim(),
                    companyID = companyId
                )

                val response = apolloClient.mutation(
                    CreateStoreMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createStore
                if (data != null) {
                    val storeModel = StoreModel(
                        id = data.id,
                        name = data.name,
                        address = data.address,
                        phone = data.phone,
                        companyId = data.companyId,
                        company = data.company.let { company ->
                            CompanyInfo(
                                id = company.id,
                                name = company.name
                            )
                        },
                        createdAt = data.createdAt,
                        updatedAt = data.updatedAt
                    )
                    emit(BaseResponse.Success(storeModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLStoreDataSource", "CreateStore error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}




