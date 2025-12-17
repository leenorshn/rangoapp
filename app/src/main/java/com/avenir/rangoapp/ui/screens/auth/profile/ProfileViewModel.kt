package com.avenir.rangoapp.ui.screens.auth.profile

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.domaine.UserRepositoryImpl
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository:AuthRepository,
    private  val userRepository: UserRepositoryImpl
): BaseViewModel<UserState,ProfileEvent>() {

    var state= MutableStateFlow(UserState())
    var loggedState= MutableStateFlow(LoggedState())

    init {
        onTriggerEvent(ProfileEvent.OnLoadUser)
    }

    override fun onTriggerEvent(event: ProfileEvent) {
        when(event){
            ProfileEvent.OnLogout -> {
                viewModelScope.launch {
                    repository.logout().collect{
                        when(it){
                            is BaseResponse.Error -> {
                                loggedState.value=loggedState.value.copy(
                                    error = it.error,
                                    isLoading = false,
                                   isLogged = false
                                )
                            }
                            BaseResponse.Loading -> {
                                loggedState.value=loggedState.value.copy(
                                    error = null,
                                    isLoading = true,
                                    isLogged = false
                                )
                            }
                            is BaseResponse.Success -> {
                                loggedState.value=loggedState.value.copy(
                                    error = null,
                                    isLoading = false,
                                    isLogged = false // Logout réussi = déconnecté
                                )
                            }
                        }
                    }
                }
            }

            ProfileEvent.OnLoadUser -> {
                viewModelScope.launch {
                    userRepository.getUsers().collect{
                        when(it){
                            is BaseResponse.Error -> {
                                state.value=state.value.copy(
                                    error = it.error,
                                    isLoading = false,

                                    user = null
                                )
                            }
                            BaseResponse.Loading -> {
                                state.value=state.value.copy(
                                    isLoading = true,
                                    error = null,
                                    user = null
                                )
                            }
                            is BaseResponse.Success -> {
                                state.value=state.value.copy(
                                    user = if(it.data.isEmpty()) null else it.data[0],
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