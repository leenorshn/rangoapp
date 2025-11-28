package com.avenir.rangoapp.data.models

data class StoreModel(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val companyId: String,
    val company: CompanyInfo? = null,
    val createdAt: String,
    val updatedAt: String
)

data class CompanyInfo(
    val id: String,
    val name: String
)




