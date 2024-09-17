package com.avenir.rangoapp.ui.screens.facture.facturation

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.domaine.VenteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FactureViewModel @Inject constructor(
    private val repository: VenteRepositoryImpl
):BaseViewModel<FactureState,FactureEvent> (){

    val state = MutableStateFlow(FactureState())

    override fun onTriggerEvent(event: FactureEvent) {
        when(event){
            FactureEvent.OnFactureLoaded -> {
                getFactures()
            }
            is FactureEvent.OnSaveFacture -> TODO()
        }
    }

    //private save
    private fun createFacture(){
        viewModelScope.launch {
//            repository.createVente().collect{
//
//            }
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