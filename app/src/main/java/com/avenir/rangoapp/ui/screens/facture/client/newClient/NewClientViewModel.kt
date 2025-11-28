package com.avenir.rangoapp.ui.screens.facture.client.newClient

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewClientViewModel @Inject constructor(
    private val repository: ClientRepository
) : BaseViewModel<NewClientState, NewClientEvent>() {
    
    val state = MutableStateFlow(NewClientState())

    override fun onTriggerEvent(event: NewClientEvent) {
        when (event) {
            is NewClientEvent.OnNameChanged -> {
                state.value = state.value.copy(name = event.name)
            }
            is NewClientEvent.OnPhoneChanged -> {
                state.value = state.value.copy(phone = event.phone)
            }
            NewClientEvent.OnSubmit -> {
                createClient()
            }
        }
    }

    private fun createClient() {
        viewModelScope.launch {
            repository.createClient(
                name = state.value.name,
                phone = state.value.phone
            ).collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            isLoading = false,
                            success = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = true,
                            success = false
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = false,
                            success = true
                        )
                    }
                }
            }
        }
    }
}

