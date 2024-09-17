package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class RapportStoreModel(
    val id: String,
    val type: String,
    val productId: String,
    val date:String,
    val quantity:Int,
    val productName:String,
)

fun Document<Map<String,Any>>.toRapportStoreModel():RapportStoreModel{
    return RapportStoreModel(
        id=this.id,
        type=this.data["type"] as String,
        productId = this.data["productId"] as String,
        quantity = this.data["quantity"] as Int,
        productName = this.data["productName"] as String,
        date = this.createdAt,
    )
}