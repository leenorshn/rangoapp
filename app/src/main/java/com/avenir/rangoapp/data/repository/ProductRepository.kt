package com.avenir.rangoapp.data.repository

import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository  {

    suspend fun createProduct(name:String,mark:String,priceVente:Double,priceAchat:Double,stock:Int):Flow<BaseResponse<Boolean>>
    suspend fun updateProduct(product:ProductModel):Flow<BaseResponse<Boolean>>
    suspend fun deleteProduct(id:String):Flow<BaseResponse<Boolean>>
    suspend fun getAllProducts():Flow<BaseResponse<List<ProductModel>>>
    suspend fun getProduct(id: String):Flow<BaseResponse<ProductModel>>
}