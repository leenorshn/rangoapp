package com.avenir.rangoapp.ui.screens.auth.login

import com.avenir.rangoapp.data.models.GraphQLSession

data class LoginState(
    var phone: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: GraphQLSession? = null,
    val isLogged: Boolean = false
)

sealed class LoginEvent {
    data object OnLogin : LoginEvent()
    data class OnPhoneChange(val name: String):LoginEvent()
    data class OnPasswordChange(val password: String):LoginEvent()

}