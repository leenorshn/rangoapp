package com.avenir.rangoapp.ui.screens.auth.register.store

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.AuthRepository
import com.avenir.rangoapp.data.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val authRepository: AuthRepository
) : BaseViewModel<StoreState, StoreEvent>() {

    var state = mutableStateOf(StoreState())
    private set

    override fun onTriggerEvent(event: StoreEvent) {
        when(event) {
            is StoreEvent.NameChanged -> {
                state.value = state.value.copy(name = event.name)
            }
            is StoreEvent.AddressChanged -> {
                state.value = state.value.copy(address = event.address)
            }
            is StoreEvent.PhoneChanged -> {
                state.value = state.value.copy(phone = event.phone)
            }
            StoreEvent.OnSubmit -> {
                createStore()
            }
        }
    }

    private fun createStore() {
        viewModelScope.launch {
            // Validate required fields
            if (state.value.name.isBlank() || state.value.address.isBlank() || state.value.phone.isBlank()) {
                state.value = state.value.copy(
                    error = "Tous les champs sont requis",
                    isLoading = false
                )
                return@launch
            }

            // Get current user to obtain companyId
            authRepository.getCurrentUser().collect { userResponse ->
                when (userResponse) {
                    is BaseResponse.Success -> {
                        val companyId = userResponse.data.companyId
                        if (companyId.isNullOrBlank()) {
                            state.value = state.value.copy(
                                error = "Impossible de récupérer l'ID de l'entreprise",
                                isLoading = false
                            )
                            return@collect
                        }

                        // Create store with companyId
                        storeRepository.createStore(
                            name = state.value.name.trim(),
                            address = state.value.address.trim(),
                            phone = state.value.phone.trim(),
                            companyId = companyId
                        ).collect { storeResponse ->
                            when (storeResponse) {
                                is BaseResponse.Error -> {
                                    state.value = state.value.copy(
                                        error = storeResponse.error,
                                        isLoading = false,
                                        isSuccess = false
                                    )
                                }
                                BaseResponse.Loading -> {
                                    state.value = state.value.copy(
                                        isLoading = true,
                                        isSuccess = false,
                                        error = null
                                    )
                                }
                                is BaseResponse.Success -> {
                                    state.value = state.value.copy(
                                        isLoading = false,
                                        isSuccess = true,
                                        error = null
                                    )
                                }
                            }
                        }
                    }
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = userResponse.error,
                            isLoading = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}



