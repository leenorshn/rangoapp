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

