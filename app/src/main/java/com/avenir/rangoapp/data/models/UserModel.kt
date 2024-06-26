package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class UserModel(
    val uid:String,
    val name:String,
    val phone:String,
    val role:String,
    val isBlocked:Boolean
)

fun Document<Map<String, Any>>.toUserModel(): UserModel {
    return UserModel(
        uid = this.id,
        name = this.data["name"] as String,
        phone = this.data["phone"] as String,
        role = this.data["role"] as String,
        isBlocked = this.data["isBlocked"] as Boolean
    )
}
