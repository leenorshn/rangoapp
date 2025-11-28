package com.avenir.rangoapp.ui.screens.auth.register.store

data class StoreState(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val error: String? = null
)

sealed class StoreEvent {
    data class NameChanged(val name: String): StoreEvent()
    data class AddressChanged(val address: String): StoreEvent()
    data class PhoneChanged(val phone: String): StoreEvent()
}



