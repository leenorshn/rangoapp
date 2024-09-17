package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class ProductModel(
    val id: String,
   val name: String,
    val mark: String,
   val priceVente: Double,
    val priceAchat: Double,
    val stock: Int=0,
    val  company:String?
)

fun Document<Map<String, Any>>.toProductModel(): ProductModel {
    return ProductModel(
        id=this.id,
        name = this.data["name"] as String,
        mark = this.data["mark"] as String,
        priceAchat = this.data["priceAchat"] as Double,
        priceVente = this.data["priceVente"] as Double,
        stock = this.data["stock"] as Int,
        company = this.data["company"] as String
    )
}
