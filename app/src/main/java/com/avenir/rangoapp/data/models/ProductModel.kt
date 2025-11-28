package com.avenir.rangoapp.data.models

data class ProductModel(
    val id: String,
    val name: String,
    val mark: String,
    val priceVente: Number,
    val priceAchat: Number,
    val stock: Number = 0,
    val storeId: String,
    val store: StoreInfo? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class StoreInfo(
    val id: String,
    val name: String,
    val address: String? = null,
    val phone: String? = null
)
