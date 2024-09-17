package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class RapportStoreModel(
    val id: String,
    val type: String,
    val product: ProductModel,
    val date:String,
    val quantity:Number,
)



fun Document<Map<String,Any>>.toRapportStoreModel():RapportStoreModel{

    val productRaw = this.data["product"] as Map<*, *>

    val productModel = ProductModel(
        id = productRaw["\$id"] as String,
        name = productRaw["name"] as String,
        mark = productRaw["mark"] as String,
        priceVente = productRaw["priceVente"] as Number,
        priceAchat = productRaw["priceAchat"] as Number,
        stock = productRaw["stock"] as Number,
        company = productRaw["company"] as String?
    )
    return RapportStoreModel(
        id=this.id,
        type=this.data["type"] as String,
        product = productModel,
        quantity = this.data["quantity"] as Number,
        date = this.createdAt,
    )
}