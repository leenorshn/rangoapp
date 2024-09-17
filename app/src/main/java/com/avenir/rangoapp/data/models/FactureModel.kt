package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

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


fun Document<Map<String,Any>>.toFactureModel():FactureModel{
    return FactureModel(
        id=this.id,
        price = this.data["price"] as Double,
        quantity = this.data["quantity"] as Int,
        factureNumber = this.data["factureNumero"] as String,
        currency = this.data["currency"] as String,
        client = this.data["client"] as ClientModel,
        products = this.data["products"] as List<ProductModel> ,
        date = this.data["date"] as String
    );
}
