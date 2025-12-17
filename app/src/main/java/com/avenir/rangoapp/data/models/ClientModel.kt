package com.avenir.rangoapp.data.models

data class ClientModel(
    val id: String,
    val name: String,
    val phone: String,
    val storeId: String,
    val store: StoreInfo? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
