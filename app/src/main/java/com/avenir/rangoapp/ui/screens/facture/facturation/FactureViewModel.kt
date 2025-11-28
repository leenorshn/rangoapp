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
            FactureEvent.OnFactureLoaded -> {
                getFactures()
            }
            is FactureEvent.OnSaveFacture -> {
                createFacture(event)
            }
        }
    }

    //private save
    private fun createFacture(event: FactureEvent.OnSaveFacture){
        viewModelScope.launch {
            repository.createVente(
                products = event.products,
                clientId = event.clientId,
                quantity = event.quantity,
                price = event.price,
                date = event.date,
                currency = event.currency
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
                        // Reload factures after successful creation
                        getFactures()
                    }
                }
            }
        }
    }
    //private loadData
    private fun getFactures(){
        viewModelScope.launch {
            repository.getFactures().collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            isLoading = false,
                            factures = listOf()
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = true,
                            factures = listOf()
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = false,
                            factures = it.data
                        )
                    }
                }
            }
        }
    }
}