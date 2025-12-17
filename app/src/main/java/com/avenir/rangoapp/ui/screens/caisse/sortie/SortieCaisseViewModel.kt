package com.avenir.rangoapp.ui.screens.caisse.sortie

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.datasource.CompanyDataStore
import com.avenir.rangoapp.data.repository.CaisseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortieCaisseViewModel @Inject constructor(
    private val repository: CaisseRepository,
    private val companyDataStore: CompanyDataStore
) : BaseViewModel<SortieCaisseState, SortieCaisseEvent>() {
    
    val state = MutableStateFlow(SortieCaisseState())

    override fun onTriggerEvent(event: SortieCaisseEvent) {
        when (event) {
            is SortieCaisseEvent.OnAmountChanged -> {
                state.value = state.value.copy(amount = event.amount)
            }
            is SortieCaisseEvent.OnDescriptionChanged -> {
                state.value = state.value.copy(description = event.description)
            }
            is SortieCaisseEvent.OnReferenceChanged -> {
                state.value = state.value.copy(reference = event.reference)
            }
            is SortieCaisseEvent.OnCurrencyChanged -> {
                state.value = state.value.copy(currency = event.currency)
            }
            SortieCaisseEvent.OnSubmit -> {
                addSortie()
            }
        }
    }

    private fun addSortie() {
        viewModelScope.launch {
            if (state.value.amount <= 0) {
                state.value = state.value.copy(
                    error = "Le montant doit être supérieur à 0",
                    isLoading = false,
                    success = false
                )
                return@launch
            }

            if (state.value.description.isBlank()) {
                state.value = state.value.copy(
                    error = "La description est requise",
                    isLoading = false,
                    success = false
                )
                return@launch
            }

            // Get storeId from CompanyDataStore
            val storeId = companyDataStore.readCompanyData()
            if (storeId.isNullOrEmpty()) {
                state.value = state.value.copy(
                    error = "Store ID is required",
                    isLoading = false,
                    success = false
                )
                return@launch
            }

            repository.createCaisseTransaction(
                amount = state.value.amount,
                operation = "Sortie",
                description = state.value.description,
                currency = state.value.currency,
                storeId = storeId,
                date = null
            ).collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            isLoading = false,
                            success = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = true,
                            success = false
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = false,
                            success = true
                        )
                    }
                }
            }
        }
    }
}
