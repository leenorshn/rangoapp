package com.avenir.rangoapp.data.models

import io.appwrite.models.Document

data class CompanyModel(
    val id: Int,
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
        id = this.id.toInt(),
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


 val CompanyModelExample =CompanyModel(
    id = 1,
    name = "Zaako",
    address = "Butembo/Rue President/ Galerie GTB N 59",
    phone = "+243999999999",
     type = "Shop-numerique",
    description = "Nous vendons des produits de qualité,habits, telephone et autres original",
    logo = "logo",
     email="email",
     rccm = "RCCm",
     idNat = "idNat",
     idCommerce = "idCommerce"
)
