package com.avenir.rangoapp.ui.screens.stock.provider

import com.avenir.rangoapp.data.models.ProviderModel


data class ProviderState(
    val lists:List<ProviderModel> = listOf(),
    val isLoading:Boolean=false,
    val error:String?=null
)

sealed class ProviderEvent{
    data class OnProviderCreated(
        val name:String,
        val phone:String,
        val address:String
    ):ProviderEvent()
    data object OnProviderLoaded:ProviderEvent()
}