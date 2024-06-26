package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.runtime.mutableStateOf
import com.avenir.rangoapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(): BaseViewModel<RegisterState,RegisterEvent>() {

    var state= mutableStateOf(RegisterState())
    private set

    override fun onTriggerEvent(event: RegisterEvent) {
        when(event){
            is RegisterEvent.AddressChanged -> {
                state.value=state.value.copy(address=event.address)
            }
            is RegisterEvent.CityChanged -> {
                state.value=state.value.copy(city=event.city)
            }
            is RegisterEvent.EmailChanged -> {
                state.value=state.value.copy(email = event.email)
            }
            is RegisterEvent.IdCommerceChanged -> {
                state.value=state.value.copy(idCommerce = event.idCommerce)
            }
            is RegisterEvent.IdNatChanged -> {
                state.value=state.value.copy(idNat = event.idNat)
            }
            is RegisterEvent.LogoChanged -> {
                state.value=state.value.copy(logo = event.logo)
            }
            is RegisterEvent.NameChanged -> {
                state.value=state.value.copy(name = event.name)
            }
            is RegisterEvent.PhoneChanged -> {
                state.value=state.value.copy(phone = event.phone)
            }
            is RegisterEvent.RccmChanged -> {
                state.value=state.value.copy(rccm=event.rccm)
            }
            RegisterEvent.SubmitFinal -> TODO()
            is RegisterEvent.TypeChanged -> {
                state.value=state.value.copy(type = event.type)
            }
        }
    }

}