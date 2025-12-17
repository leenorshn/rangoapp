package com.avenir.rangoapp.data.models

data class CaisseModel(
    val currentBalance: Double,
    val inAmount: Double, // "in" est un mot réservé en Kotlin
    val outAmount: Double, // "out" est un mot réservé en Kotlin
    val currency: String,
    val storeId: String? = null,
    val store: StoreInfo? = null
)

data class CaisseTransactionModel(
    val id: String,
    val amount: Double,
    val operation: String, // "Entree" or "Sortie"
    val description: String,
    val currency: String,
    val storeId: String,
    val store: StoreInfo? = null,
    val date: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
