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
    private val repository: AuthRepository
): BaseViewModel<RegisterState, RegisterEvent>() {

    var state= mutableStateOf(RegisterState())
    private set

    override fun onTriggerEvent(event: RegisterEvent) {
        when(event){
            is RegisterEvent.NameChanged -> {
                state.value=state.value.copy(username = event.name)
            }
            RegisterEvent.Submit -> {
                onSubmit()
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

   private fun onSubmit() {
        viewModelScope.launch {
            repository.createUser(
                username = state.value.username,
                password = state.value.password,
            ).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(error = it.error, isLoading = false, isSuccess = false)
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(isLoading = true, isSuccess = false, error = null)
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(isLoading = false, isSuccess = true, error = null)
                    }
                }
            }
        }
    }

    private fun checkPassword() {
        if (state.value.password != state.value.confirmPassword) {
            state.value = state.value.copy(error = "Les mots de passe ne correspondent pas",
                isTwoPasswordValid = false)
        }else{
            state.value = state.value.copy(isTwoPasswordValid = true)
        }

    }

}