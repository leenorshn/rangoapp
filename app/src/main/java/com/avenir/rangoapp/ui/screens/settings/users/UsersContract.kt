package com.avenir.rangoapp.ui.screens.settings.users

import com.avenir.rangoapp.data.models.UserModel


data class UsersState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val users: List<UserModel>? = null,
    val showSuccessMessage: Boolean = false
)

sealed class UsersEvent {
    data object OnLoadUsers : UsersEvent()
    data class OnDeleteUser(val userId: String) : UsersEvent()
    data class OnBlockUser(val userId: String) : UsersEvent()
    data class OnUnblockUser(val userId: String) : UsersEvent()
    data object OnRefreshUsers : UsersEvent()
}