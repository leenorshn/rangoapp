package com.avenir.rangoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainState(

    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false

)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


    init {
        checkUserState()
    }

    fun checkUserState() {
        viewModelScope.launch {
            authRepository.isUserLoggedIn()
                .collect { it ->
                      when (it) {
                        is BaseResponse.Error -> {
                            _state.value =  _state.value.copy(isLoggedIn = false, error = it.error, isLoading = false)
                        }

                        BaseResponse.Loading -> {
                            _state.value=   _state.value.copy(isLoading = true, isLoggedIn = false, error = null)
                        }
                        is BaseResponse.Success -> {
                            _state.value=  _state.value.copy(isLoggedIn = it.data, error = null, isLoading = false)
                        }
                    }
                }
        }
    }
    
    fun refreshAuthState() {
        checkUserState()
    }

}


