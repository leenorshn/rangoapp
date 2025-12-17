package com.avenir.rangoapp.ui.screens.facture.client.newClient

data class NewClientState(
    val name: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class NewClientEvent {
    data class OnNameChanged(val name: String) : NewClientEvent()
    data class OnPhoneChanged(val phone: String) : NewClientEvent()
    data object OnSubmit : NewClientEvent()
}
