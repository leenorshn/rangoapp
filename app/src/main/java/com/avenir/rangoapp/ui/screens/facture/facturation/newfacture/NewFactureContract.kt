package com.avenir.rangoapp.ui.screens.facture.facturation.newfacture

import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.models.ProductModel
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

// Helper function to convert LocalDate to RFC3339 format
fun LocalDate.toRFC3339(): String {
    return this.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
}

data class NewFactureState(
    val date: String = LocalDate.now().toRFC3339(),
    val currency: String = "USD",
    val selectedClient: ClientModel? = null,
    val selectedProducts: List<Pair<ProductModel, Int>> = emptyList(), // Product and quantity
    val availableProducts: List<ProductModel> = emptyList(),
    val availableClients: List<ClientModel> = emptyList(),
    val isTvaEnabled: Boolean = true,
    val subtotal: Double = 0.0,
    val tvaAmount: Double = 0.0,
    val total: Double = 0.0,
    val totalQuantity: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class NewFactureEvent {
    data class OnDateChanged(val date: String) : NewFactureEvent()
    data class OnCurrencyChanged(val currency: String) : NewFactureEvent()
    data class OnClientSelected(val client: ClientModel) : NewFactureEvent()
    data class OnTvaEnabledChanged(val enabled: Boolean) : NewFactureEvent()
    data class OnProductAdded(val product: ProductModel, val quantity: Int) : NewFactureEvent()
    data class OnProductRemoved(val productId: String) : NewFactureEvent()
    data class OnProductQuantityChanged(val productId: String, val quantity: Int) : NewFactureEvent()
    data object OnSaveDraft : NewFactureEvent()
    data object OnSaveInvoice : NewFactureEvent()
}

