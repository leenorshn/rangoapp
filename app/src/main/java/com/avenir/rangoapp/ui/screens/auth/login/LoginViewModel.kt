package com.avenir.rangoapp.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.domaine.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set


    fun onEvent(event: LoginEvent){
        when(event){
            LoginEvent.OnLogin -> {
                loginUser()
            }
            is LoginEvent.OnPasswordChange -> {
                state=state.copy(password = (event.password))
            }
            is LoginEvent.OnPhoneChange -> {
                state=state.copy(phone = (event.name))
            }
        }
    }

    private fun loginUser(){
         viewModelScope.launch {
             repository.login(state.phone,state.password).collect{it->
                 state = when(it){
                     is BaseResponse.Error -> {
                         state.copy(error = it.error, isLoading = false, user = null)
                     }

                     BaseResponse.Loading -> {
                         state.copy(isLoading = true, error = null, user = null)
                     }

                     is BaseResponse.Success -> {
                         state.copy(user = it.data, isLoading = false, error = null)
                     }
                 }
             }
         }
     }

}