package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.ClientsQuery
import com.avenir.rangoapp.graphql.CreateClientMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLClientDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val companyDataStore: CompanyDataStore
) {
    
    suspend fun getClients(storeId: String? = null): Flow<BaseResponse<List<ClientModel>>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(
                    ClientsQuery(
                        com.apollographql.apollo3.api.Optional.presentIfNotNull(storeId)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val clients = response.data?.clients?.mapNotNull { client ->
                    if (client != null) {
                        ClientModel(
                            id = client.id,
                            name = client.name,
                            phone = client.phone
                        )
                    } else {
                        null
                    }
                } ?: emptyList()

                emit(BaseResponse.Success(clients))
            } catch (ex: Exception) {
                Log.e("GraphQLClientDataSource", "GetClients error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun createClient(
        name: String,
        phone: String,
        storeId: String? = null
    ): Flow<BaseResponse<ClientModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                // Si storeId n'est pas fourni, utiliser celui du store actuel
                val currentStoreId = storeId ?: companyDataStore.readCompanyData()
                
                if (currentStoreId.isNullOrEmpty()) {
                    emit(BaseResponse.Error("Store ID is required"))
                    return@flow
                }

                // Validation des champs requis
                if (name.isBlank() || phone.isBlank()) {
                    emit(BaseResponse.Error("Le nom et le téléphone sont requis"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.CreateClientInput(
                    name = name.trim(),
                    phone = phone.trim(),
                    storeId = currentStoreId
                )

                val response = apolloClient.mutation(
                    CreateClientMutation(input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.createClient
                if (data != null) {
                    val clientModel = ClientModel(
                        id = data.id,
                        name = data.name,
                        phone = data.phone
                    )
                    emit(BaseResponse.Success(clientModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLClientDataSource", "CreateClient error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}

