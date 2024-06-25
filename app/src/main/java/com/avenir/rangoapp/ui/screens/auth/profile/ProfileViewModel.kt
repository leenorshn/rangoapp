package com.avenir.rangoapp.ui.screens.auth.profile

import androidx.lifecycle.viewModelScope
import com.avenir.rangoapp.core.BaseViewModel
import com.avenir.rangoapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository:AuthRepository
): BaseViewModel<ViewState,ProfileEvent>() {
    override fun onTriggerEvent(event: ProfileEvent) {
        when(event){
            ProfileEvent.OnLogout -> {
                viewModelScope.launch {
                    repository.logout()
                }
            }
        }
    }

}