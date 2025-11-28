package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLRapportStoreDataSource
import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.data.repository.RapportStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RapportStoreRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLRapportStoreDataSource
):RapportStoreRepository {
    override suspend fun createRapport(
        productId: String,
        quantity: Int,
        type: String
    ): Flow<BaseResponse<Boolean>> {
        return dataSource.createRapport(productId, quantity, type)
    }

    override suspend fun getRapportStore(): Flow<BaseResponse<List<RapportStoreModel>>> {
        return dataSource.getRapportStore()
    }
}