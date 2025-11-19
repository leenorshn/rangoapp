package com.avenir.rangoapp.ui.screens.auth.register.company

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<ViewState, CompanyEvent>() {

    var state = mutableStateOf(ViewState())
    private set
    
    // Method to set user data from RegisterViewModel
    fun setUserData(email: String, password: String, name: String, phone: String) {
        state.value = state.value.copy(
            userEmail = email,
            userPassword = password,
            userName = name,
            userPhone = phone
        )
    }
    
    override fun onTriggerEvent(event: CompanyEvent) {
        when(event) {
            is CompanyEvent.AddressChanged -> {
                state.value = state.value.copy(address = event.address)
            }
            is CompanyEvent.LogoChanged -> {
                state.value = state.value.copy(logo = event.logo)
            }
            is CompanyEvent.NameChanged -> {
                state.value = state.value.copy(name = event.name)
            }
            is CompanyEvent.PhoneChanged -> {
                state.value = state.value.copy(phone = event.phone)
            }
            is CompanyEvent.EmailChanged -> {
                state.value = state.value.copy(email = event.email)
            }
            is CompanyEvent.DescriptionChanged -> {
                state.value = state.value.copy(description = event.description)
            }
            is CompanyEvent.TypeChanged -> {
                state.value = state.value.copy(type = event.type)
            }
            is CompanyEvent.RccmChanged -> {
                state.value = state.value.copy(rccm = event.rccm)
            }
            is CompanyEvent.IdNatChanged -> {
                state.value = state.value.copy(idNat = event.idNat)
            }
            is CompanyEvent.IdCommerceChanged -> {
                state.value = state.value.copy(idCommerce = event.idCommerce)
            }
            is CompanyEvent.StoreNameChanged -> {
                state.value = state.value.copy(storeName = event.storeName)
            }
            is CompanyEvent.StoreAddressChanged -> {
                state.value = state.value.copy(storeAddress = event.storeAddress)
            }
            is CompanyEvent.StorePhoneChanged -> {
                state.value = state.value.copy(storePhone = event.storePhone)
            }
            CompanyEvent.OnSubmit -> {
                register()
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            // Use company phone as user phone if user phone is empty
            val userPhone = if (state.value.userPhone.isNotEmpty()) {
                state.value.userPhone
            } else {
                state.value.phone
            }
            
            // Use company name as user name if user name is empty
            val userName = if (state.value.userName.isNotEmpty()) {
                state.value.userName
            } else {
                state.value.name.split(" ").firstOrNull() ?: state.value.name
            }
            
            // Use store name as company name if company name is empty, otherwise use company name
            val companyName = if (state.value.name.isNotEmpty()) {
                state.value.name
            } else {
                state.value.storeName
            }
            
            // Default values for required fields
            val companyDescription = state.value.description.ifEmpty { "Entreprise créée via l'application" }
            val companyType = state.value.type.ifEmpty { "SARL" }
            
            authRepository.register(
                email = state.value.userEmail,
                password = state.value.userPassword,
                name = userName,
                phone = userPhone,
                companyName = companyName,
                companyAddress = state.value.address,
                companyPhone = state.value.phone,
                companyDescription = companyDescription,
                companyType = companyType,
                storeName = state.value.storeName.ifEmpty { "Boutique Principale" },
                storeAddress = state.value.storeAddress.ifEmpty { state.value.address },
                storePhone = state.value.storePhone.ifEmpty { state.value.phone },
                companyEmail = state.value.email.takeIf { it.isNotEmpty() },
                companyLogo = state.value.logo.takeIf { it.isNotEmpty() },
                companyRccm = state.value.rccm.takeIf { it.isNotEmpty() },
                companyIdNat = state.value.idNat.takeIf { it.isNotEmpty() },
                companyIdCommerce = state.value.idCommerce.takeIf { it.isNotEmpty() }
            ).collect {
                when(it) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = it.error,
                            isLoading = false,
                            isSuccess = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            isLoading = true,
                            isSuccess = false,
                            error = null
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            error = null
                        )
                    }
                }
            }
        }
    }
}