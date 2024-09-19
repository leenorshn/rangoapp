package com.avenir.rangoapp.ui.screens.settings.users

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.domaine.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
): BaseViewModel<UsersState, UsersEvent>() {
    var state= MutableStateFlow(UsersState())

    init {
        onTriggerEvent(UsersEvent.OnLoadUsers)
    }

    override fun onTriggerEvent(event: UsersEvent) {
        when(event){
            UsersEvent.OnLoadUsers -> {
                viewModelScope.launch {
                    userRepository.getUsers().collect{
                        when(it){
                            is BaseResponse.Error -> {
                                state.value=state.value.copy(
                                    users = null,
                                    isLoading = false,
                                    error = it.error
                                )
                            }
                            BaseResponse.Loading -> {
                                state.value=state.value.copy(
                                    users = null,
                                    isLoading = true,
                                    error = null
                                )
                            }
                            is BaseResponse.Success -> {
                                state.value=state.value.copy(
                                    users = it.data,
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