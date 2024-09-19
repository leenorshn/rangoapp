package com.avenir.rangoapp.ui.screens.settings.shop

import com.avenir.rangoapp.data.models.CompanyModel

data class ShopState(
    val name: String="",
    val phone: String="",
    val address: String="",
    val description: String?="",
    val rccm: String? = "",
    val logo: String? ="",
    val idCommerce:String?="",



    val isLoading: Boolean = false,
    val error: String? = null,
    val shop: CompanyModel?= null

)

sealed class ShopEvent {
    data object OnLoadShops : ShopEvent()
    data class OnNameChanged(val name: String) : ShopEvent()
    data class OnPhoneChanged(val phone: String) : ShopEvent()
    data class OnAddressChanged(val address: String) : ShopEvent()
    data class OnDescriptionChanged(val description: String) : ShopEvent()
    data class OnRccmChanged(val rccm: String) : ShopEvent()
    data class OnLogoChanged(val logo: String) : ShopEvent()
    data class OnIdCommerceChanged(val idCommerce: String) : ShopEvent()

}