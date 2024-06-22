package com.avenir.rangoapp.data.models

data class CompanyModel(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val description: String,
    val domain: String,
    val logo: String,
)

 val CompanyModelExample =CompanyModel(
    id = 1,
    name = "Zaako",
    address = "Butembo/Rue President/ Galerie GTB N 59",
    phone = "+243999999999",
     domain = "Shop-numerique",
    description = "Nous vendons des produits de qualité,habits, telephone et autres original",
    logo = "logo",
)
