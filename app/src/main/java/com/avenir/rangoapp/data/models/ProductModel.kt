package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class ProductModel(
    val id: String,
   val name: String,
    val mark: String,
   val priceVente: Number,
    val priceAchat: Number,
    val stock: Number=0,
    val  company:String?
)


fun Document<Map<String, Any>>.toProductModel(): ProductModel {
    return ProductModel(
        id=this.id,
        name = this.data["name"] as String,
        mark = this.data["mark"] as String,
        priceAchat = this.data["priceAchat"] as Number,
        priceVente = this.data["priceVente"] as Number,
        stock = this.data["stock"] as Number,
        company = this.data["company"] as String
    )
}
