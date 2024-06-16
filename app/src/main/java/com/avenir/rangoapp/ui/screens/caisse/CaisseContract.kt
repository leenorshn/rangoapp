package com.avenir.rangoapp.ui.screens.caisse


data class Trans(
    val amount: Double,
    val operation: String,
    val libel: String,
    val currency: String
)

val listOfTrans = listOf(
    Trans(
        amount = 1200.0,
        operation = "entre",
        libel = "depot initial",
        currency = "USD"
    ), Trans(
        amount = 200.0,
        operation = "sortie",
        libel = "Louage magasin",
        currency = "USD"
    ), Trans(
        amount = 600.0,
        operation = "sortie",
        libel = "Achat fourniture",
        currency = "USD"
    ), Trans(
        amount = 1000.0,
        operation = "entre",
        libel = "depot du capital actif",
        currency = "USD"
    )
)
