package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLStoreDataSource
import com.avenir.rangoapp.data.models.StoreModel
import com.avenir.rangoapp.data.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val graphQLStoreDataSource: GraphQLStoreDataSource
) : StoreRepository {

    override suspend fun getStores(): Flow<BaseResponse<List<StoreModel>>> {
        return graphQLStoreDataSource.getStores()
    }

    override suspend fun createStore(
        name: String,
        address: String,
        phone: String
    ): Flow<BaseResponse<StoreModel>> {
        return graphQLStoreDataSource.createStore(name, address, phone)
    }
}




