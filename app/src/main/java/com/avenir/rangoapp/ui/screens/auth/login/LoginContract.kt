package com.avenir.rangoapp.ui.screens.auth.login

import io.appwrite.models.Session


data class LoginState(
    var phone:String="",
    var password:String="",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: Session? = null
)

sealed class LoginEvent {
    data object OnLogin : LoginEvent()
    data class OnPhoneChange(val name: String):LoginEvent()
    data class OnPasswordChange(val password: String):LoginEvent()

}