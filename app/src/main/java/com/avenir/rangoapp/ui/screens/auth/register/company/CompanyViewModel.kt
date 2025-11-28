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
    private val companyRepository: CompanyRepository
) : BaseViewModel<ViewState, CompanyEvent>() {

    var state = mutableStateOf(ViewState())
    private set
    
    override fun onTriggerEvent(event: CompanyEvent) {
        when(event) {
            is CompanyEvent.NameChanged -> {
                state.value = state.value.copy(name = event.name)
            }
            is CompanyEvent.PhoneChanged -> {
                state.value = state.value.copy(phone = event.phone)
            }
            is CompanyEvent.EmailChanged -> {
                state.value = state.value.copy(email = event.email)
            }
            is CompanyEvent.TypeChanged -> {
                state.value = state.value.copy(type = event.type)
            }
            CompanyEvent.OnSubmit -> {
                createCompany()
            }
        }
    }

    private fun createCompany() {
        viewModelScope.launch {
            // Validate required fields
            if (state.value.name.isBlank() || state.value.phone.isBlank()) {
                state.value = state.value.copy(
                    error = "Le nom et le téléphone sont requis",
                    isLoading = false
                )
                return@launch
            }
            
            // Default values for required fields
            val companyDescription = state.value.description.ifEmpty { "not definie yet" }
            val companyType = state.value.type.ifEmpty { "Commerce" }
            
            companyRepository.createCompany(
                name = state.value.name.trim(),
                address = state.value.address.ifEmpty { "Non spécifiée" }, // Required by schema
                phone = state.value.phone.trim(),
                email = state.value.email.takeIf { it.isNotBlank() },
                description = companyDescription,
                type = companyType,
                logo = null,
                rccm = null,
                idNat = null,
                idCommerce = null
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