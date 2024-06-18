package com.avenir.rangoapp.data.models

data class ProductModel(
    val id: String,
   val name: String,
  val  mark: String,
    val category: String,
   val priceVente: Double,
    val priceAchat: Double,
    val charge: Double=0.0,
    val stock: Int=0
)
