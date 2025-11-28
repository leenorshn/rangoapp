package com.avenir.rangoapp.data.models

data class RapportStoreModel(
    val id: String,
    val type: String,
    val product: ProductModel,
    val date: String,
    val quantity: Number,
    val storeId: String? = null,
    val store: StoreInfo? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)