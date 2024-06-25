package com.avenir.rangoapp.ui.screens.auth.login

import androidx.compose.ui.text.input.TextFieldValue
import io.appwrite.models.User


data class LoginState(
    var phone:TextFieldValue=TextFieldValue(""),
    var password:TextFieldValue=TextFieldValue(""),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val user: User<Map<String, Any>>? = null
)

sealed class LoginEvent {
    data object OnLogin : LoginEvent()

}