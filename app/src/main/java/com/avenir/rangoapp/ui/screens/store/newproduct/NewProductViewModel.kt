package com.avenir.rangoapp.ui.screens.store.newproduct

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.domaine.ProductRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewProductViewModel @Inject constructor(
    private val repository: ProductRepositoryImpl
):BaseViewModel<NewProductState,NewProductEvent>() {
    val state = MutableStateFlow(NewProductState())
    override fun onTriggerEvent(event: NewProductEvent) {
        when(event){
            is NewProductEvent.OnCreateProduct -> createProduct()
        }
    }

   private fun createProduct(){
        viewModelScope.launch {
            repository.createProduct(
                name = state.value.name,
                mark = state.value.mark,
                priceVente = state.value.priceVente,
                priceAchat = state.value.priceAchat,
                stock = state.value.stock
            ).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            isLoading = false,
                            success = false,
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = true,
                            success = false,
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            error = null,
                            isLoading = false,
                            success = true,
                        )
                    }
                }
            }
        }
    }
}


