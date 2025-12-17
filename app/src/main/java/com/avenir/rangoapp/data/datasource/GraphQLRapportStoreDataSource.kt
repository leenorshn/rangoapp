package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.models.StoreInfo
// import com.avenir.rangoapp.graphql.RapportStoreQuery // Query n'existe pas dans le schéma
// import com.avenir.rangoapp.graphql.CreateRapportStoreMutation // Mutation n'existe pas dans le schéma
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GraphQLRapportStoreDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    private val companyDataStore: CompanyDataStore
) {
    
    // TODO: La query rapportStore n'existe pas dans le schéma GraphQL actuel
    suspend fun getRapportStore(storeId: String? = null): Flow<BaseResponse<List<RapportStoreModel>>> {
        return flow {
            emit(BaseResponse.Error("La query rapportStore n'est pas disponible dans le schéma GraphQL actuel."))
        }
    }

    // TODO: La mutation createRapportStore n'existe pas dans le schéma GraphQL actuel
    suspend fun createRapport(
        productId: String,
        quantity: Number,
        type: String,
        storeId: String? = null
    ): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Error("La mutation createRapportStore n'est pas disponible dans le schéma GraphQL actuel."))
        }
    }
}

