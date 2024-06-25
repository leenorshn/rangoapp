package com.avenir.rangoapp.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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



     fun loginUser(){
         viewModelScope.launch {
             repository.login(state.phone.text,state.password.text)
         }
     }

}