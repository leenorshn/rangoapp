package com.avenir.rangoapp.ui.screens.facture.facturation.newfacture

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.models.ClientModel
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.repository.ClientRepository
import com.avenir.rangoapp.data.repository.ProductRepository
import com.avenir.rangoapp.data.repository.VenteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import com.avenir.rangoapp.ui.screens.facture.facturation.newfacture.toRFC3339
import javax.inject.Inject

@HiltViewModel
class NewFactureViewModel @Inject constructor(
    private val venteRepository: VenteRepository,
    private val productRepository: ProductRepository,
    private val clientRepository: ClientRepository
) : BaseViewModel<NewFactureState, NewFactureEvent>() {
    
    val state = MutableStateFlow(NewFactureState())

    init {
        loadProducts()
        loadClients()
    }
    
    private fun loadClients() {
        viewModelScope.launch {
            clientRepository.getClients().collect { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(availableClients = response.data)
                    }
                    else -> {
                        // Handle error silently or show message
                    }
                }
            }
        }
    }

    override fun onTriggerEvent(event: NewFactureEvent) {
        when (event) {
            is NewFactureEvent.OnDateChanged -> {
                state.value = state.value.copy(date = event.date)
            }
            is NewFactureEvent.OnCurrencyChanged -> {
                state.value = state.value.copy(currency = event.currency)
            }
            is NewFactureEvent.OnClientSelected -> {
                state.value = state.value.copy(selectedClient = event.client)
            }
            is NewFactureEvent.OnTvaEnabledChanged -> {
                state.value = state.value.copy(isTvaEnabled = event.enabled)
                calculateTotal()
            }
            is NewFactureEvent.OnProductAdded -> {
                addProduct(event.product, event.quantity)
            }
            is NewFactureEvent.OnProductRemoved -> {
                removeProduct(event.productId)
            }
            is NewFactureEvent.OnProductQuantityChanged -> {
                updateProductQuantity(event.productId, event.quantity)
            }
            NewFactureEvent.OnSaveDraft -> {
                saveFacture(isDraft = true)
            }
            NewFactureEvent.OnSaveInvoice -> {
                saveFacture(isDraft = false)
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(availableProducts = response.data)
                    }
                    else -> {
                        // Handle error silently or show message
                    }
                }
            }
        }
    }

    private fun addProduct(product: ProductModel, quantity: Int) {
        val currentProducts = state.value.selectedProducts.toMutableList()
        val existingIndex = currentProducts.indexOfFirst { it.first.id == product.id }
        
        if (existingIndex >= 0) {
            // Update quantity if product already exists
            val (existingProduct, existingQuantity) = currentProducts[existingIndex]
            currentProducts[existingIndex] = existingProduct to (existingQuantity + quantity)
        } else {
            // Add new product
            currentProducts.add(product to quantity)
        }
        
        state.value = state.value.copy(selectedProducts = currentProducts)
        calculateTotal()
    }

    private fun removeProduct(productId: String) {
        val currentProducts = state.value.selectedProducts.toMutableList()
        currentProducts.removeAll { it.first.id == productId }
        state.value = state.value.copy(selectedProducts = currentProducts)
        calculateTotal()
    }

    private fun updateProductQuantity(productId: String, quantity: Int) {
        val currentProducts = state.value.selectedProducts.toMutableList()
        val index = currentProducts.indexOfFirst { it.first.id == productId }
        
        if (index >= 0 && quantity > 0) {
            val (product, _) = currentProducts[index]
            currentProducts[index] = product to quantity
            state.value = state.value.copy(selectedProducts = currentProducts)
            calculateTotal()
        } else if (quantity <= 0) {
            removeProduct(productId)
        }
    }

    private fun calculateTotal() {
        val subtotal = state.value.selectedProducts.sumOf { (product, quantity) ->
            product.priceVente.toDouble() * quantity
        }
        
        val tvaAmount = if (state.value.isTvaEnabled) {
            subtotal * 0.16 // 16% TVA
        } else {
            0.0
        }
        
        val total = subtotal + tvaAmount
        
        state.value = state.value.copy(
            subtotal = subtotal,
            tvaAmount = tvaAmount,
            total = total,
            totalQuantity = state.value.selectedProducts.sumOf { it.second }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveFacture(isDraft: Boolean) {
        if (state.value.selectedClient == null) {
            state.value = state.value.copy(error = "Veuillez sélectionner un client")
            return
        }
        
        if (state.value.selectedProducts.isEmpty()) {
            state.value = state.value.copy(error = "Veuillez ajouter au moins un produit")
            return
        }

        viewModelScope.launch {
            // Convert products to basket format: List<Triple<productId, quantity, price>>
            val basket = state.value.selectedProducts.map { (product, quantity) ->
                Triple(
                    product.id,
                    quantity.toDouble(),
                    product.priceVente.toDouble()
                )
            }
            
            val date = state.value.date.ifEmpty {
                LocalDate.now().toRFC3339()
            }

            val priceToPay = state.value.total
            // For now, assume pricePayed equals priceToPay (exact payment)
            // In a real scenario, this should come from the UI (payment screen)
            val pricePayed = priceToPay

            venteRepository.createSale(
                basket = basket,
                priceToPay = priceToPay,
                pricePayed = pricePayed,
                clientId = state.value.selectedClient!!.id,
                storeId = null, // Will be determined by the repository from CompanyDataStore
                currency = state.value.currency,
                date = date
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
                    is BaseResponse.Success<*> -> {
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

