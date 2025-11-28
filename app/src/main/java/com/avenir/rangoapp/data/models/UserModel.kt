package com.avenir.rangoapp.data.models

data class UserModel(
    val uid: String,
    val name: String,
    val phone: String,
    val role: String,
    val isBlocked: Boolean,
    val companyId: String? = null,
    val storeIds: List<String>? = null,
    val assignedStoreId: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)


