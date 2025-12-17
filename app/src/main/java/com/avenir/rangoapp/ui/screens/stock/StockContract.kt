package com.avenir.rangoapp.ui.screens.stock

import com.avenir.rangoapp.data.models.ProductModel

data class StoreState(
    val products:List<ProductModel> = listOf(),
    val error:String?=null,
    val isLoading:Boolean=false,
    val showSuccessMessage:Boolean = false
)

sealed class StoreEvent{
    data object OnLoadProduct:StoreEvent()
    data object OnRefreshProducts:StoreEvent()
}

