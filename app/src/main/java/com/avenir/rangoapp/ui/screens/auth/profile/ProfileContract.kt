package com.avenir.rangoapp.ui.screens.auth.profile

import com.avenir.rangoapp.data.models.UserModel


sealed class ProfileEvent{
    data object OnLogout:ProfileEvent()
    data object OnLoadUser:ProfileEvent()
}

data class UserState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,

)

data class LoggedState(
    val isLogged: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)