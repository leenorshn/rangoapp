package com.avenir.rangoapp.ui.screens.caisse

import com.avenir.rangoapp.data.models.CaisseModel

data class CaisseState(
    val caisse: CaisseModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class CaisseEvent {
    data object OnLoadCaisse : CaisseEvent()
    data object OnRefreshCaisse : CaisseEvent()
}
