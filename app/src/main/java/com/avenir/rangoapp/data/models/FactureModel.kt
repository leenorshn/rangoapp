package com.avenir.rangoapp.data.models

data class FactureModel(
    val id: String,
    val factureNumber: String,
    val products: List<FactureProductModel>,
    val quantity: Int,
    val date: String,
    val price: Double,
    val currency: String,
    val client: ClientModel,
    val storeId: String? = null,
    val store: StoreInfo? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class FactureProductModel(
    val productId: String,
    val product: ProductModel,
    val quantity: Int,
    val price: Double
)
