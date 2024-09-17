package com.avenir.rangoapp.ui.screens.store.rapport

import com.avenir.rangoapp.data.models.RapportStoreModel


data class RapportStoreState(
    val rapports: List<RapportStoreModel> = listOf(),
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class RapportStoreEvent{
    data object OnRapportLoad:RapportStoreEvent()
}