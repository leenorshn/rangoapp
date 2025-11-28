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

    override suspend fun createClient(
        name: String,
        phone: String,
        storeId: String?
    ): Flow<BaseResponse<ClientModel>> {
        return dataSource.createClient(name, phone, storeId)
    }
}

