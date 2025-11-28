package com.avenir.rangoapp.data.models

data class FactureModel(
    val id:String,
    val products:List<ProductModel>,
    val quantity:Int,
    val date:String,
    val factureNumber:String,
    val price:Double,
    val currency:String,
    val client:ClientModel
)
