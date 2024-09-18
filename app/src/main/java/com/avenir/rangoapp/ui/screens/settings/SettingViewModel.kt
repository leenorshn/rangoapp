package com.avenir.rangoapp.ui.screens.settings

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.domaine.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: UserRepositoryImpl
) :BaseViewModel<UserState,UserEvent>(){
    val state= MutableStateFlow(UserState())
    override fun onTriggerEvent(event: UserEvent) {
        when(event){
            UserEvent.OnLoadUser -> {
                viewModelScope.launch {
                    repository.getUsers().collect{
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
                                    user = it.data[0],
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