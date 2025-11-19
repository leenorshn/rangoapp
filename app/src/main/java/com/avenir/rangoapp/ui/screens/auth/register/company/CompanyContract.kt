package com.avenir.rangoapp.ui.screens.auth.register.company

data class ViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    
    // Company info
    val name: String = "",
    val logo: String = "",
    val phone: String = "",
    val address: String = "",
    val email: String = "",
    val description: String = "",
    val type: String = "",
    val rccm: String = "",
    val idNat: String = "",
    val idCommerce: String = "",
    
    // Store info (first store)
    val storeName: String = "",
    val storeAddress: String = "",
    val storePhone: String = "",
    
    // User info from Step 1 (passed from RegisterViewModel)
    val userEmail: String = "",
    val userPassword: String = "",
    val userName: String = "",
    val userPhone: String = ""
)

sealed class CompanyEvent {
    data class NameChanged(val name: String): CompanyEvent()
    data class LogoChanged(val logo: String): CompanyEvent()
    data class PhoneChanged(val phone: String): CompanyEvent()
    data class AddressChanged(val address: String): CompanyEvent()
    data class EmailChanged(val email: String): CompanyEvent()
    data class DescriptionChanged(val description: String): CompanyEvent()
    data class TypeChanged(val type: String): CompanyEvent()
    data class RccmChanged(val rccm: String): CompanyEvent()
    data class IdNatChanged(val idNat: String): CompanyEvent()
    data class IdCommerceChanged(val idCommerce: String): CompanyEvent()
    data class StoreNameChanged(val storeName: String): CompanyEvent()
    data class StoreAddressChanged(val storeAddress: String): CompanyEvent()
    data class StorePhoneChanged(val storePhone: String): CompanyEvent()
    data object OnSubmit: CompanyEvent()
}