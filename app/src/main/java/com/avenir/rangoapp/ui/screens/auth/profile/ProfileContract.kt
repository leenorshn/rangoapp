package com.avenir.rangoapp.ui.screens.auth.profile

import io.appwrite.models.User


sealed class ProfileEvent{
    data object OnLogout:ProfileEvent()

}

data class ViewState(
    val user: User<Map<String,Any>>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,

)