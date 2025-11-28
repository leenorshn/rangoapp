package com.avenir.rangoapp.ui.screens.auth.register.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
): BaseViewModel<RegisterState, RegisterEvent>() {

    var state= mutableStateOf(RegisterState())
    private set

    override fun onTriggerEvent(event: RegisterEvent) {
        when(event){
            is RegisterEvent.NameChanged -> {
                state.value=state.value.copy(name = event.name)
            }
            RegisterEvent.Submit -> {
                register()
            }
            is RegisterEvent.PasswordChanged -> {
                state.value=state.value.copy(password = event.password)
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                state.value=state.value.copy(confirmPassword = event.confirmPassword)
                checkPassword()
            }
            is RegisterEvent.PhoneChanged -> {
                state.value=state.value.copy(phone = event.phone)
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            // Validate fields
            if (!state.value.isTwoPasswordValid) {
                state.value = state.value.copy(
                    error = "Les mots de passe ne correspondent pas",
                    isLoading = false
                )
                return@launch
            }
            
            if (state.value.name.isBlank() || state.value.phone.isBlank() || state.value.password.isBlank()) {
                state.value = state.value.copy(
                    error = "Veuillez remplir tous les champs",
                    isLoading = false
                )
                return@launch
            }

            authRepository.register(
                password = state.value.password,
                name = state.value.name.trim(),
                phone = state.value.phone.trim()
            ).collect {
                when(it) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = it.error,
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
    }

    private fun checkPassword() {
        if (state.value.password != state.value.confirmPassword) {
            state.value = state.value.copy(
                error = "Les mots de passe ne correspondent pas",
                isTwoPasswordValid = false
            )
        } else {
            state.value = state.value.copy(
                isTwoPasswordValid = true,
                error = null
            )
        }
    }
}