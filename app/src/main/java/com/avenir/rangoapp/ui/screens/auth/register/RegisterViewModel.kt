package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel<RegisterState,RegisterEvent>() {

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
            RegisterEvent.SubmitFinal -> {
                onSubmit()
            }
            is RegisterEvent.TypeChanged -> {
                state.value=state.value.copy(type = event.type)
            }

            is RegisterEvent.PasswordChanged -> {
                state.value=state.value.copy(password = event.password)
            }
        }
    }

   private fun onSubmit() {
        viewModelScope.launch {
            repository.createAccount(
                name = state.value.name,
                email = state.value.email,
                password = state.value.password,
                phone = state.value.phone,
                rccm = state.value.rccm,
                idNat = state.value.idNat,
                idCommerce = state.value.idCommerce,
                logo = state.value.logo.orEmpty(),
                address = state.value.address,
                type = state.value.type,
                city = state.value.city,
                description = "Aucune description",
                role = "Admin"
            ).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(error = it.error, isLoading = false, isSuccess = false)
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(isLoading = true, isSuccess = false, error = null)
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(isLoading = false, isSuccess = true, error = null)
                    }
                }
            }
        }
    }

}