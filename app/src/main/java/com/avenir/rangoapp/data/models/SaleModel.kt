package com.avenir.rangoapp.data.models

data class SaleModel(
    val id: String,
    val basket: List<SaleProductModel>,
    val priceToPay: Double,
    val pricePayed: Double,
    val change: Double,
    val currency: String,
    val client: ClientModel,
    val operator: UserInfo? = null,
    val storeId: String,
    val store: StoreInfo? = null,
    val date: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class SaleProductModel(
    val productId: String,
    val product: ProductModel,
    val quantity: Double,
    val price: Double
)

data class UserInfo(
    val id: String,
    val name: String
)


