package com.avenir.rangoapp.data.domaine

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.datasource.GraphQLCaisseDataSource
import com.avenir.rangoapp.data.models.CaisseModel
import com.avenir.rangoapp.data.models.CaisseTransactionModel
import com.avenir.rangoapp.data.repository.CaisseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CaisseRepositoryImpl @Inject constructor(
    private val dataSource: GraphQLCaisseDataSource
) : CaisseRepository {
    
    override suspend fun getCaisse(
        storeId: String?,
        currency: String?,
        period: String?
    ): Flow<BaseResponse<CaisseModel>> {
        return dataSource.getCaisse(storeId, currency, period)
    }

    override suspend fun getCaisseTransactions(
        storeId: String?,
        currency: String?,
        period: String?,
        limit: Int?
    ): Flow<BaseResponse<List<CaisseTransactionModel>>> {
        return dataSource.getCaisseTransactions(storeId, currency, period, limit)
    }

    override suspend fun createCaisseTransaction(
        amount: Double,
        operation: String,
        description: String,
        currency: String,
        storeId: String,
        date: String?
    ): Flow<BaseResponse<CaisseTransactionModel>> {
        return dataSource.createCaisseTransaction(amount, operation, description, currency, storeId, date)
    }

    override suspend fun deleteCaisseTransaction(id: String): Flow<BaseResponse<Boolean>> {
        return dataSource.deleteCaisseTransaction(id)
    }
}
