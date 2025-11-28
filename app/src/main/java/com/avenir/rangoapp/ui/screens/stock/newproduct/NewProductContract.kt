package com.avenir.rangoapp.ui.screens.stock.newproduct


data class NewProductState(
    var name:String="",
    var mark:String="",
    var priceVente:Number=0,
    var priceAchat:Number=0,
    val stock:Number=1,


    val isLoading:Boolean?=false,
    val error:String?=null,
    val success:Boolean?=false
)

sealed class NewProductEvent{
    data object OnSubmit:NewProductEvent()
    data class OnNameChanged(val name:String):NewProductEvent()
    data class OnMarlChanged(val mark:String):NewProductEvent()
    data class OnPriceVenteChanged(val priceVente: Number):NewProductEvent()
    data class OnPriceAchatChanged(val priceAchat: Number):NewProductEvent()
    data class OnStockChanged(val stock: Number):NewProductEvent()

}