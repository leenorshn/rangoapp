package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLClientDataSource
import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLClientDataSource
) : ClientRepository {
    
    override suspend fun getClients(storeId: String?): Flow<BaseResponse<List<ClientModel>>> {
        return dataSource.getClients(storeId)
    }

    override suspend fun getClient(id: String): Flow<BaseResponse<ClientModel>> {
        return dataSource.getClient(id)
    }

    override suspend fun createClient(
        name: String,
        phone: String,
        storeId: String?
    ): Flow<BaseResponse<ClientModel>> {
        return dataSource.createClient(
            name = name,
            phone = phone,
            storeId = storeId
        )
    }

    override suspend fun updateClient(
        id: String,
        name: String?,
        phone: String?
    ): Flow<BaseResponse<ClientModel>> {
        return dataSource.updateClient(
            id = id,
            name = name,
            phone = phone
        )
    }

    override suspend fun deleteClient(id: String): Flow<BaseResponse<Boolean>> {
        return dataSource.deleteClient(id)
    }
}
