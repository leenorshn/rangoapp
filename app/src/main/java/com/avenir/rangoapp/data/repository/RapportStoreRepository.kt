package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.RapportStoreModel
import kotlinx.coroutines.flow.Flow

interface RapportStoreRepository {

    suspend fun createRapport(productId:String,
                              productName:String,
                              type:String):Flow<BaseResponse<Boolean>>
    suspend fun getRapportStore():Flow<BaseResponse<List<RapportStoreModel>>>
}