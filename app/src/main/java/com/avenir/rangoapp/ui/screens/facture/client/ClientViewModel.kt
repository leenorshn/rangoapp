package com.avenir.rangoapp.ui.screens.facture.client

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val repository: ClientRepository
) : BaseViewModel<ClientState, ClientEvent>() {
    
    val state = MutableStateFlow(ClientState())

    init {
        onTriggerEvent(ClientEvent.OnLoadClients)
    }

    override fun onTriggerEvent(event: ClientEvent) {
        when (event) {
            ClientEvent.OnLoadClients -> {
                loadClients()
            }
            ClientEvent.OnRefreshClients -> {
                loadClients()
            }
            is ClientEvent.OnClientSelected -> {
                state.value = state.value.copy(selectedClient = event.client)
            }
            is ClientEvent.OnDeleteClient -> {
                deleteClient(event.clientId)
            }
            is ClientEvent.OnSearchClients -> {
                searchClients(event.query)
            }
        }
    }

    private fun loadClients() {
        viewModelScope.launch {
            repository.getClients().collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            isLoading = false,
                            clients = emptyList(),
                            showSuccessMessage = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = true,
                            clients = emptyList(),
                            showSuccessMessage = false
                        )
                    }
                    is BaseResponse.Success -> {
                        val shouldShowMessage = state.value.showSuccessMessage
                        state.value = state.value.copy(
                            error = null,
                            isLoading = false,
                            clients = response.data,
                            showSuccessMessage = shouldShowMessage
                        )
                        // Réinitialiser le message après un court délai
                        if (shouldShowMessage) {
                            kotlinx.coroutines.delay(100)
                            state.value = state.value.copy(showSuccessMessage = false)
                        }
                    }
                }
            }
        }
    }

    private fun deleteClient(clientId: String) {
        viewModelScope.launch {
            repository.deleteClient(clientId).collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            deleteSuccess = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            deleteSuccess = false
                        )
                    }
                    is BaseResponse.Success -> {
                        if (response.data) {
                            state.value = state.value.copy(
                                error = null,
                                deleteSuccess = true
                            )
                            // Recharger la liste après suppression
                            loadClients()
                        } else {
                            state.value = state.value.copy(
                                error = "Échec de la suppression",
                                deleteSuccess = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun searchClients(query: String) {
        viewModelScope.launch {
            // Note: The new API doesn't support search filter, so we load all clients
            // and filter them locally if needed
            repository.getClients(storeId = null).collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            isLoading = false,
                            clients = emptyList()
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = true,
                            clients = emptyList()
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = false,
                            clients = response.data
                        )
                    }
                }
            }
        }
    }
}
