package com.avenir.rangoapp.ui.screens.store.rapport

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.domaine.RapportStoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RapportStoreViewModel @Inject constructor(
    private val repository: RapportStoreRepositoryImpl
):BaseViewModel<RapportStoreState,RapportStoreEvent>() {
    val state= MutableStateFlow(RapportStoreState())
    init {
        onTriggerEvent(RapportStoreEvent.OnRapportLoad)
    }

    override fun onTriggerEvent(event: RapportStoreEvent) {
        when(event){
            RapportStoreEvent.OnRapportLoad -> {
                viewModelScope.launch {
                    repository.getRapportStore()
                        .collect{
                            when(it){
                                is BaseResponse.Error -> {
                                    state.value=state.value.copy(
                                        rapports = listOf(),
                                        error = it.error,
                                        isLoading = false,
                                    )
                                }
                                BaseResponse.Loading -> {
                                    state.value=state.value.copy(
                                        isLoading = true,
                                        rapports = listOf(),
                                        error = null
                                    )
                                }
                                is BaseResponse.Success ->{
                                    state.value=state.value.copy(
                                        rapports = it.data,
                                        error = null,
                                        isLoading = false,
                                    )
                                }
                            }
                        }
                }
            }
        }
    }
}