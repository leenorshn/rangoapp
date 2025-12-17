package com.avenir.rangoapp.ui.screens.caisse.enter

data class EnterCaisseState(
    val amount: Double = 0.0,
    val description: String = "",
    val reference: String = "",
    val currency: String = "USD",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class EnterCaisseEvent {
    data class OnAmountChanged(val amount: Double) : EnterCaisseEvent()
    data class OnDescriptionChanged(val description: String) : EnterCaisseEvent()
    data class OnReferenceChanged(val reference: String) : EnterCaisseEvent()
    data class OnCurrencyChanged(val currency: String) : EnterCaisseEvent()
    data object OnSubmit : EnterCaisseEvent()
}



