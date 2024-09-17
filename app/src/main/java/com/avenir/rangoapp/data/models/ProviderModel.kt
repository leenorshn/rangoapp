package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class ProviderModel(
    val id:String,
    val name:String,
    val phone:String,
    val address:String,
)

fun Document<Map<String,Any>>.toProviderModel():ProviderModel{
    return ProviderModel(
        id = this.id,
        name = this.data["name"] as String,
        phone = this.data["phone"] as String,
        address = this.data["address"] as String
    )
}
