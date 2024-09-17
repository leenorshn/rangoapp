package com.avenir.rangoapp.ui.screens.auth.register.company


data class ViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,


    val name: String = "",
    val logo:String="",
    val phone:String="",
    val address:String="",
    val email:String="",
)

sealed class CompanyEvent{
    data class NameChanged(val name:String): CompanyEvent()
    data class LogoChanged(val logo:String): CompanyEvent()
    data class PhoneChanged(val phone:String): CompanyEvent()
    data class AddressChanged(val address:String): CompanyEvent()
    data class EmailChanged(val email:String):CompanyEvent()
    data object OnSubmit: CompanyEvent()
}