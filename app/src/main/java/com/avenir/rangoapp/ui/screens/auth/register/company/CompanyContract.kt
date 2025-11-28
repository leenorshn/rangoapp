package com.avenir.rangoapp.ui.screens.auth.register.company

data class ViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    
    // Company info (required in step 2)
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val type: String = "",
    
    // Optional fields (not shown in step 2 image)
    val description: String = "",
    val address: String = ""
)

sealed class CompanyEvent {
    data class NameChanged(val name: String): CompanyEvent()
    data class PhoneChanged(val phone: String): CompanyEvent()
    data class EmailChanged(val email: String): CompanyEvent()
    data class TypeChanged(val type: String): CompanyEvent()
    data object OnSubmit: CompanyEvent()
}