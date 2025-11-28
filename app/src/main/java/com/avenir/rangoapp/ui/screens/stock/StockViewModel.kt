package com.avenir.rangoapp.ui.screens.stock

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: ProductRepository
):BaseViewModel<StoreState,StoreEvent>() {
    val state= MutableStateFlow(StoreState())

    init {
        onTriggerEvent(StoreEvent.OnLoadProduct)
    }

    override fun onTriggerEvent(event: StoreEvent) {
        when(event){
            StoreEvent.OnLoadProduct->{
                viewModelScope.launch {
                    repository.getAllProducts().collect{
                        when(it){
                            is BaseResponse.Error -> {
                                state.value=state.value.copy(
                                    error = it.error,
                                    isLoading = false,
                                    products = listOf()
                                )
                            }
                            BaseResponse.Loading -> {
                                state.value=state.value.copy(
                                    error = null,
                                    isLoading = true,
                                    products = listOf()
                                )
                            }
                            is BaseResponse.Success -> {
                                state.value=state.value.copy(
                                    error = null,
                                    isLoading = false,
                                    products = it.data
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}