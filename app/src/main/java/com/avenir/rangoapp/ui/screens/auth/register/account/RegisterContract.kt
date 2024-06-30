package com.avenir.rangoapp.ui.screens.auth.register.account

data class RegisterState(
    val username:String="",
    val password:String="",
    val confirmPassword:String="",

    val  isLoading:Boolean=false,
    val  error:String?=null,
    val  isSuccess:Boolean=false,

    val isTwoPasswordValid:Boolean=false,
)

sealed class RegisterEvent{
    data class NameChanged(val name:String): RegisterEvent()
    data class PasswordChanged(val password:String): RegisterEvent()
    data class ConfirmPasswordChanged(val confirmPassword:String): RegisterEvent()
    data object Submit: RegisterEvent()
}