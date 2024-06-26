package com.avenir.rangoapp.ui.screens.auth.register

data class RegisterState(
    val name:String="",
    val type:String="",
    val address:String="",
    val phone:String="",
    val email:String="",
    val city:String="",
    val logo:String?=null,
    val rccm:String="",
    val idNat:String="",
    val idCommerce:String="",
    val password:String="",
)

sealed class RegisterEvent{
    data class NameChanged(val name:String):RegisterEvent()
    data class TypeChanged(val type:String):RegisterEvent()
    data class AddressChanged(val address:String):RegisterEvent()
    data class PhoneChanged(val phone:String):RegisterEvent()
    data class EmailChanged(val email:String):RegisterEvent()
    data class CityChanged(val city:String):RegisterEvent()
    data class PasswordChanged(val password:String):RegisterEvent()
    data class LogoChanged(val logo:String):RegisterEvent()
    data class RccmChanged(val rccm:String):RegisterEvent()
    data class IdNatChanged(val idNat:String):RegisterEvent()
    data class IdCommerceChanged(val idCommerce:String):RegisterEvent()
    object SubmitFinal:RegisterEvent()


}