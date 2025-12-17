package com.avenir.rangoapp.ui.screens.caisse.transaction

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.CaisseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionCaisseViewModel @Inject constructor(
    private val repository: CaisseRepository
) : BaseViewModel<TransactionCaisseState, TransactionCaisseEvent>() {
    
    val state = MutableStateFlow(TransactionCaisseState())

    init {
        onTriggerEvent(TransactionCaisseEvent.OnLoadTransactions)
    }

    override fun onTriggerEvent(event: TransactionCaisseEvent) {
        when (event) {
            TransactionCaisseEvent.OnLoadTransactions -> {
                loadTransactions()
            }
            TransactionCaisseEvent.OnRefreshTransactions -> {
                loadTransactions()
            }
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            repository.getCaisseTransactions().collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            isLoading = false,
                            transactions = emptyList()
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = true,
                            transactions = emptyList()
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = false,
                            transactions = response.data
                        )
                    }
                }
            }
        }
    }
}



