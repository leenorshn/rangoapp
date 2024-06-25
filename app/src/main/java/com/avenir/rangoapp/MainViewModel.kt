package com.avenir.rangoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainState(

    val isLoggedIn: Boolean = false,

)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {

    var state by mutableStateOf(MainState())
    private set

    init {
        checkUserState()
    }

   private fun checkUserState(){
        viewModelScope.launch {
            authRepository.isUserLoggedIn()
                .collect {it ->
                    state = when(it){
                        true -> {
                            state.copy(isLoggedIn = true,)
                        }

                        false -> {
                            state.copy(isLoggedIn = false)
                        }
                    }
                }
        }
    }

}