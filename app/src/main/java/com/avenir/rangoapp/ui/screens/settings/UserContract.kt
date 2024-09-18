package com.avenir.rangoapp.ui.screens.settings

import com.avenir.rangoapp.data.models.UserModel

data class UserState(
    val user:UserModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false
)

sealed class UserEvent{
    data object OnLoadUser:UserEvent()
}