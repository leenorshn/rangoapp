package com.avenir.rangoapp.data.models

data class CompanyModel(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val description: String,
    val type: String,
    val logo: String? = null,
    val rccm: String? = null,
    val idNat: String? = null,
    val idCommerce: String? = null,
    val stores: List<StoreInfo>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)



