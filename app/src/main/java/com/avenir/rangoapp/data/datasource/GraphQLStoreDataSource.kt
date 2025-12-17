package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.StoreModel
import com.avenir.rangoapp.data.models.CompanyInfo
import com.avenir.rangoapp.graphql.CreateStoreMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLStoreDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    
    // TODO: La query stores n'existe pas dans le schéma GraphQL actuel
    suspend fun getStores(): Flow<BaseResponse<List<StoreModel>>> {
        return flow {
            emit(BaseResponse.Error("La query stores n'est pas disponible dans le schéma GraphQL actuel."))
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
                // Validation des champs requis
                if (name.isBlank() || address.isBlank() || phone.isBlank() || companyId.isBlank()) {
                    emit(BaseResponse.Error("Les champs nom, adresse, téléphone et companyId sont requis"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.CreateStoreInput(
                    name = name.trim(),
                    address = address.trim(),
                    phone = phone.trim(),
                    companyID = companyId.trim()
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




