package com.avenir.rangoapp.ui.screens.settings

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :BaseViewModel<UserState,UserEvent>(){
    val state= MutableStateFlow(UserState())

    init {
        onTriggerEvent(UserEvent.OnLoadUser)
    }
    override fun onTriggerEvent(event: UserEvent) {
        when(event){
            UserEvent.OnLoadUser -> {
                viewModelScope.launch {
                    authRepository.getCurrentUser().collect{
                        when(it){
                            is BaseResponse.Error -> {
                                state.value=state.value.copy(
                                    user = null,
                                    isLoading = false,
                                    error = it.error
                                )
                            }
                            BaseResponse.Loading -> {
                                state.value=state.value.copy(
                                    user = null,
                                    isLoading = true,
                                    error = null
                                )
                            }
                            is BaseResponse.Success -> {
                                state.value=state.value.copy(
                                    user = it.data,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}