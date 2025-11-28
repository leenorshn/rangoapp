package com.avenir.rangoapp.ui.screens.settings.shop

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.CompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: CompanyRepository
): BaseViewModel<ShopState, ShopEvent>() {

    val state= MutableStateFlow(ShopState())

    init {
        onTriggerEvent(ShopEvent.OnLoadShops)
    }

    override fun onTriggerEvent(event: ShopEvent) {
        when(event){
            is ShopEvent.OnAddressChanged -> {
                state.value=state.value.copy(address = event.address)
            }
            is ShopEvent.OnDescriptionChanged -> {
                state.value=state.value.copy(description = event.description)
            }
            is ShopEvent.OnIdCommerceChanged -> {
                state.value=state.value.copy(idCommerce = event.idCommerce)
            }
            ShopEvent.OnLoadShops ->{
                loadShop()
            }
            is ShopEvent.OnLogoChanged -> {
                state.value=state.value.copy(logo = event.logo)
            }
            is ShopEvent.OnNameChanged -> {
                state.value=state.value.copy(name = event.name)
            }
            is ShopEvent.OnPhoneChanged -> {
                state.value=state.value.copy(phone = event.phone)
            }
            is ShopEvent.OnRccmChanged -> {
                state.value=state.value.copy(rccm = event.rccm)
            }
        }
    }

  private  fun loadShop(){
        viewModelScope.launch {
            repository.getCompany().collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            isLoading = false,
                            shop = null
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            error =null,
                            isLoading = true,
                            shop = null
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = false,
                            shop = it.data
                        )
                    }
                }
            }
        }
    }
}