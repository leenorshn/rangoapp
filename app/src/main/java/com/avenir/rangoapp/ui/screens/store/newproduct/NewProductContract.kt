package com.avenir.rangoapp.ui.screens.store.newproduct


data class NewProductState(
    val name:String="",
    val mark:String="",
    val priceVente:Double=0.0,
    val priceAchat:Double=0.0,
    val stock:Int=1,


    val isLoading:Boolean?=false,
    val error:String?=null,
    val success:Boolean?=false
)

sealed class NewProductEvent{
    data class OnCreateProduct(val data:NewProductState):NewProductEvent()
}