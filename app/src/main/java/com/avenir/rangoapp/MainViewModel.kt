package com.avenir.rangoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var state by mutableStateOf(MainState())
        private set

    init {
        checkUserState()
    }

    private fun checkUserState() {
        viewModelScope.launch {
            authRepository.isUserLoggedIn()
                .collect { it ->
                    state = when (it) {
                        is BaseResponse.Error -> {
                            state.copy(isLoggedIn = false, error = it.error, isLoading = false)
                        }

                        BaseResponse.Loading -> {
                            state.copy(isLoading = true, isLoggedIn = true, error = null)
                        }
                        is BaseResponse.Success -> {
                            state.copy(isLoggedIn = true, error = null, isLoading = false)
                        }
                    }
                }
        }
    }

}