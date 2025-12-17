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
            UsersEvent.OnLoadUsers, UsersEvent.OnRefreshUsers -> {
                loadUsers()
            }
            is UsersEvent.OnDeleteUser -> {
                deleteUser(event.userId)
            }
            is UsersEvent.OnBlockUser -> {
                blockUser(event.userId)
            }
            is UsersEvent.OnUnblockUser -> {
                unblockUser(event.userId)
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            users = null,
                            isLoading = false,
                            error = it.error,
                            showSuccessMessage = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            users = null,
                            isLoading = true,
                            error = null,
                            showSuccessMessage = false
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            users = it.data,
                            isLoading = false,
                            error = null,
                            showSuccessMessage = false
                        )
                    }
                }
            }
        }
    }

    private fun deleteUser(userId: String) {
        viewModelScope.launch {
            userRepository.deleteUser(userId).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            showSuccessMessage = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is BaseResponse.Success -> {
                        if (it.data) {
                            state.value=state.value.copy(
                                isLoading = false,
                                error = null,
                                showSuccessMessage = true
                            )
                            // Reload users after deletion
                            loadUsers()
                        }
                    }
                }
            }
        }
    }

    private fun blockUser(userId: String) {
        viewModelScope.launch {
            userRepository.blockUser(userId).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            showSuccessMessage = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            isLoading = false,
                            error = null,
                            showSuccessMessage = true
                        )
                        // Reload users after blocking
                        loadUsers()
                    }
                }
            }
        }
    }

    private fun unblockUser(userId: String) {
        viewModelScope.launch {
            userRepository.unblockUser(userId).collect{
                when(it){
                    is BaseResponse.Error -> {
                        state.value=state.value.copy(
                            error = it.error,
                            showSuccessMessage = false
                        )
                    }
                    BaseResponse.Loading -> {
                        state.value=state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is BaseResponse.Success -> {
                        state.value=state.value.copy(
                            isLoading = false,
                            error = null,
                            showSuccessMessage = true
                        )
                        // Reload users after unblocking
                        loadUsers()
                    }
                }
            }
        }
    }
}