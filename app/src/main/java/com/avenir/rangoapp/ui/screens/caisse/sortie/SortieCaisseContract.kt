package com.avenir.rangoapp.ui.screens.caisse.sortie

data class SortieCaisseState(
    val amount: Double = 0.0,
    val description: String = "",
    val reference: String = "",
    val currency: String = "USD",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class SortieCaisseEvent {
    data class OnAmountChanged(val amount: Double) : SortieCaisseEvent()
    data class OnDescriptionChanged(val description: String) : SortieCaisseEvent()
    data class OnReferenceChanged(val reference: String) : SortieCaisseEvent()
    data class OnCurrencyChanged(val currency: String) : SortieCaisseEvent()
    data object OnSubmit : SortieCaisseEvent()
}



