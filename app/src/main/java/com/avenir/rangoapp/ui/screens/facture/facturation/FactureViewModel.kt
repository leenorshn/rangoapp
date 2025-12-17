package com.avenir.rangoapp.ui.screens.facture.facturation

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.VenteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FactureViewModel @Inject constructor(
    private val repository: VenteRepository
):BaseViewModel<FactureState,FactureEvent> (){

    val state = MutableStateFlow(FactureState())

    override fun onTriggerEvent(event: FactureEvent) {
        when(event){
            FactureEvent.OnSalesLoaded -> {
                getSales()
            }
            is FactureEvent.OnCreateSale -> {
                createSale(event)
            }
        }
    }

    //private save
    private fun createSale(event: FactureEvent.OnCreateSale){
        viewModelScope.launch {
            repository.createSale(
                basket = event.basket,
                priceToPay = event.priceToPay,
                pricePayed = event.pricePayed,
                clientId = event.clientId,
                storeId = event.storeId,
                currency = event.currency,
                date = event.date
            ).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = it.error,
                            isLoading = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            error = null
                        )
                        // Reload sales after successful creation
                        getSales()
                    }
                }
            }
        }
    }
    //private loadData
    private fun getSales(){
        viewModelScope.launch {
            repository.getSales().collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            isLoading = false,
                            sales = listOf()
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = true,
                            sales = listOf()
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = false,
                            sales = it.data
                        )
                    }
                }
            }
        }
    }
}