package com.avenir.rangoapp.ui.screens.caisse.account


data class AccountCaisseModel(
    val name:String,
    val type: String,
    val currencies:List<String>
)

var listAccount= listOf<AccountCaisseModel>(
    AccountCaisseModel(
        name = "Business",
        type = "principal",
        currencies = listOf("USD","CDF")
    ),
    AccountCaisseModel(
        name = "Achat",
        type = "business",
        currencies = listOf("USD","CDF")
    ),
    AccountCaisseModel(
        name = "Famille",
        type = "famille",
        currencies = listOf("USD","CDF")
    ),
    AccountCaisseModel(
        name = "Taxe",
        type = "business",
        currencies = listOf("CDF")
    ),
)