package com.avenir.rangoapp.ui.screens.auth.register.company

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.CompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository
) : BaseViewModel<ViewState,CompanyEvent>() {

    var state = mutableStateOf(ViewState())
    private set
    override fun onTriggerEvent(event: CompanyEvent) {
        when(event){
            is CompanyEvent.AddressChanged -> {
                state.value=state.value.copy(address = event.address)
            }

            is CompanyEvent.LogoChanged -> {
                state.value=state.value.copy(logo = event.logo)
            }
            is CompanyEvent.NameChanged -> {
                state.value=state.value.copy(name = event.name)
            }
            CompanyEvent.OnSubmit -> {
                createCompany()
            }
            is CompanyEvent.PhoneChanged -> {
                state.value=state.value.copy(phone = event.phone)
            }

            is CompanyEvent.EmailChanged -> {
                state.value=state.value.copy(email = event.email)
            }
        }
    }

    private fun createCompany() {
        viewModelScope.launch {
            repository.createCompany(
                name = state.value.name,
                address = state.value.address,
                phone = state.value.phone,
                email = state.value.email
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