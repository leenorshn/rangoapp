package com.avenir.rangoapp.ui.screens.facture.client

import com.avenir.rangoapp.data.models.ClientModel

data class ClientState(
    val clients: List<ClientModel> = emptyList(),
    val selectedClient: ClientModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val deleteSuccess: Boolean = false,
    val showSuccessMessage: Boolean = false
)

sealed class ClientEvent {
    data object OnLoadClients : ClientEvent()
    data object OnRefreshClients : ClientEvent()
    data class OnClientSelected(val client: ClientModel) : ClientEvent()
    data class OnDeleteClient(val clientId: String) : ClientEvent()
    data class OnSearchClients(val query: String) : ClientEvent()
}
