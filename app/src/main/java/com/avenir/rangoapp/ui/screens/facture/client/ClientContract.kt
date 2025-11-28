package com.avenir.rangoapp.ui.screens.facture.client

import com.avenir.rangoapp.data.models.ClientModel

data class ClientState(
    val clients: List<ClientModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ClientEvent {
    data object OnLoadClients : ClientEvent()
}

