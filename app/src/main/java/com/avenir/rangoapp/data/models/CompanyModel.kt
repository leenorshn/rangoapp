package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class CompanyModel(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val description: String,
    val type: String,
    val logo: String?=null,
    val email: String?=null,
    val rccm: String?=null,
    val idNat: String?=null,
    val idCommerce: String?=null,
)


fun Document<Map<String, Any>>.toCompanyModel(): CompanyModel {
    return CompanyModel(
        id = this.id,
        name = this.data["name"] as String,
        address = this.data["address"] as String,
        phone = this.data["phone"] as String,
        description = this.data["description"] as String,
        type = this.data["type"] as String,
        logo = this.data["logo"] as String,
        email = this.data["email"] as String,
        rccm = this.data["rccm"] as String,
        idNat = this.data["idNat"] as String,
        idCommerce = this.data["idCommerce"] as String,
    )
}



