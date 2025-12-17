package com.avenir.rangoapp.ui.screens.caisse.transaction

import com.avenir.rangoapp.data.models.CaisseTransactionModel

data class TransactionCaisseState(
    val transactions: List<CaisseTransactionModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TransactionCaisseEvent {
    data object OnLoadTransactions : TransactionCaisseEvent()
    data object OnRefreshTransactions : TransactionCaisseEvent()
}



