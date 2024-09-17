package com.avenir.rangoapp.ui.screens.store.newproduct


data class NewProductState(
    var name:String="",
    var mark:String="",
    var priceVente:Double=0.0,
    var priceAchat:Double=0.0,
    val stock:Int=1,


    val isLoading:Boolean?=false,
    val error:String?=null,
    val success:Boolean?=false
)

sealed class NewProductEvent{
    data object OnSubmit:NewProductEvent()
    data class OnNameChanged(val name:String):NewProductEvent()
    data class OnMarlChanged(val mark:String):NewProductEvent()
    data class OnPriceVenteChanged(val priceVente: Double):NewProductEvent()
    data class OnPriceAchatChanged(val priceAchat: Double):NewProductEvent()
    data class OnStockChanged(val stock: Int):NewProductEvent()

}