package com.avenir.rangoapp.ui.screens.settings.users

import com.avenir.rangoapp.data.models.UserModel


data class UsersState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val users: List<UserModel>? = null
)

sealed class UsersEvent {
    data object OnLoadUsers : UsersEvent()
}