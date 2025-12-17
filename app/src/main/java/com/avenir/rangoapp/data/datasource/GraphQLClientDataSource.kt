package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.models.StoreInfo
import com.avenir.rangoapp.graphql.ClientsQuery
import com.avenir.rangoapp.graphql.ClientQuery
import com.avenir.rangoapp.graphql.CreateClientMutation
import com.avenir.rangoapp.graphql.UpdateClientMutation
import com.avenir.rangoapp.graphql.DeleteClientMutation
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
                        storeId = Optional.presentIfNotNull(storeId)
                    )
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val clients = response.data?.clients?.mapNotNull { client ->
                    ClientModel(
                        id = client.id,
                        name = client.name,
                        phone = client.phone,
                        storeId = client.storeId,
                        store = client.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = client.createdAt,
                        updatedAt = client.updatedAt
                    )
                } ?: emptyList()

                emit(BaseResponse.Success(clients))
            } catch (ex: Exception) {
                Log.e("GraphQLClientDataSource", "GetClients error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun getClient(id: String): Flow<BaseResponse<ClientModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val response = apolloClient.query(ClientQuery(id)).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val client = response.data?.client
                if (client != null) {
                    val clientModel = ClientModel(
                        id = client.id,
                        name = client.name,
                        phone = client.phone,
                        storeId = client.storeId,
                        store = client.store?.let { store ->
                            StoreInfo(
                                id = store.id,
                                name = store.name,
                                address = store.address,
                                phone = store.phone
                            )
                        },
                        createdAt = client.createdAt,
                        updatedAt = client.updatedAt
                    )
                    emit(BaseResponse.Success(clientModel))
                } else {
                    emit(BaseResponse.Error("Client not found"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLClientDataSource", "GetClient error: ${ex.message}", ex)
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
                // Validation des champs requis
                if (name.isBlank() || phone.isBlank()) {
                    emit(BaseResponse.Error("Le nom et le téléphone sont requis"))
                    return@flow
                }

                // Si storeId n'est pas fourni, utiliser celui du store actuel
                val currentStoreId = storeId ?: companyDataStore.readCompanyData()
                
                if (currentStoreId.isNullOrEmpty()) {
                    emit(BaseResponse.Error("Store ID is required"))
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
                        phone = data.phone,
                        storeId = data.storeId,
                        store = data.store?.let { store ->
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

    suspend fun updateClient(
        id: String,
        name: String? = null,
        phone: String? = null
    ): Flow<BaseResponse<ClientModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (id.isBlank()) {
                    emit(BaseResponse.Error("L'ID du client est requis"))
                    return@flow
                }

                if (name.isNullOrBlank() && phone.isNullOrBlank()) {
                    emit(BaseResponse.Error("Au moins le nom ou le téléphone doit être fourni"))
                    return@flow
                }

                val input = com.avenir.rangoapp.graphql.type.UpdateClientInput(
                    name = Optional.presentIfNotNull(name?.trim()),
                    phone = Optional.presentIfNotNull(phone?.trim())
                )

                val response = apolloClient.mutation(
                    UpdateClientMutation(id, input)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val data = response.data?.updateClient
                if (data != null) {
                    val clientModel = ClientModel(
                        id = data.id,
                        name = data.name,
                        phone = data.phone,
                        storeId = data.storeId,
                        store = data.store?.let { store ->
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
                    emit(BaseResponse.Success(clientModel))
                } else {
                    emit(BaseResponse.Error("No data returned"))
                }
            } catch (ex: Exception) {
                Log.e("GraphQLClientDataSource", "UpdateClient error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }

    suspend fun deleteClient(id: String): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                if (id.isBlank()) {
                    emit(BaseResponse.Error("L'ID du client est requis"))
                    return@flow
                }

                val response = apolloClient.mutation(
                    DeleteClientMutation(id)
                ).execute()

                if (response.hasErrors()) {
                    val errorMessage = response.errors?.firstOrNull()?.message ?: "Unknown error"
                    emit(BaseResponse.Error(errorMessage))
                    return@flow
                }

                val deleted = response.data?.deleteClient ?: false
                emit(BaseResponse.Success(deleted))
            } catch (ex: Exception) {
                Log.e("GraphQLClientDataSource", "DeleteClient error: ${ex.message}", ex)
                emit(BaseResponse.Error(ex.message ?: "Unknown error"))
            }
        }
    }
}
