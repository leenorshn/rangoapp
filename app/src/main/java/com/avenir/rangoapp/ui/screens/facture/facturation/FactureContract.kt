package com.avenir.rangoapp.ui.screens.facture.facturation

import com.avenir.rangoapp.data.models.FactureModel


data class FactureState(
    val factures:List<FactureModel> = listOf(),
    val isLoading:Boolean=false,
    val error:String?=null
)



sealed class FactureEvent{
    data object OnFactureLoaded:FactureEvent()
    data class OnSaveFacture(
        val products:List<String>,
        val client:String,
        val quantity:Int,
        val price:Double,
        val date:String,
    ):FactureEvent()
}
