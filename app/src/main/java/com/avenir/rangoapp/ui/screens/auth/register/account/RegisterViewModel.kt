package com.avenir.rangoapp.ui.screens.auth.register.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
): BaseViewModel<RegisterState, RegisterEvent>() {

    var state= mutableStateOf(RegisterState())
    private set

    override fun onTriggerEvent(event: RegisterEvent) {
        when(event){
            is RegisterEvent.NameChanged -> {
                state.value=state.value.copy(username = event.name)
            }
            RegisterEvent.Submit -> {
                // Just validate and mark as ready to proceed
                // The actual registration will happen in CompanyViewModel with all data
                if (state.value.isTwoPasswordValid && state.value.username.isNotEmpty()) {
                    state.value=state.value.copy(isSuccess = true, error = null)
                } else {
                    state.value=state.value.copy(
                        error = "Veuillez remplir tous les champs correctement",
                        isSuccess = false
                    )
                }
            }
            is RegisterEvent.PasswordChanged -> {
                state.value=state.value.copy(password = event.password)
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                state.value=state.value.copy(confirmPassword = event.confirmPassword)
                checkPassword()
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