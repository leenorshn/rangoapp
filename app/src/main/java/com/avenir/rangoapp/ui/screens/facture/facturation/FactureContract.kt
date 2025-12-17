package com.avenir.rangoapp.ui.screens.facture.facturation

import com.avenir.rangoapp.data.models.SaleModel


data class FactureState(
    val sales:List<SaleModel> = listOf(),
    val isLoading:Boolean=false,
    val error:String?=null
)



sealed class FactureEvent{
    data object OnSalesLoaded:FactureEvent()
    data class OnCreateSale(
        val basket: List<Triple<String, Double, Double>>, // List of (productId, quantity, price)
        val priceToPay: Double,
        val pricePayed: Double,
        val clientId: String,
        val storeId: String? = null,
        val currency: String,
        val date: String? = null
    ):FactureEvent()
}
