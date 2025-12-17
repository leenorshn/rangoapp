package com.avenir.rangoapp.ui.screens.caisse

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.CaisseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaisseViewModel @Inject constructor(
    private val repository: CaisseRepository
) : BaseViewModel<CaisseState, CaisseEvent>() {
    
    val state = MutableStateFlow(CaisseState())

    init {
        onTriggerEvent(CaisseEvent.OnLoadCaisse)
    }

    override fun onTriggerEvent(event: CaisseEvent) {
        when (event) {
            CaisseEvent.OnLoadCaisse -> {
                loadCaisse()
            }
            CaisseEvent.OnRefreshCaisse -> {
                loadCaisse()
            }
        }
    }

    private fun loadCaisse() {
        viewModelScope.launch {
            repository.getCaisse().collect { response ->
                when (response) {
                    is BaseResponse.Error -> {
                        state.value = state.value.copy(
                            error = response.error,
                            isLoading = false,
                            caisse = null
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = true,
                            caisse = null
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value = state.value.copy(
                            error = null,
                            isLoading = false,
                            caisse = response.data
                        )
                    }
                }
            }
        }
    }
}



