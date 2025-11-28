package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.StoreModel
import com.avenir.rangoapp.data.models.CompanyInfo
import com.avenir.rangoapp.graphql.StoresQuery
import com.avenir.rangoapp.graphql.CreateStoreMutation
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

                val stores = response.data?.stores?.mapNotNull { store ->
                    if (store != null) {
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
                    } else {
                        null
                    }
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
        phone: String
    ): Flow<BaseResponse<StoreModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val input = com.avenir.rangoapp.graphql.type.CreateStoreInput(
                    name = name,
                    address = address,
                    phone = phone
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
                        company = data.company?.let { company ->
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




