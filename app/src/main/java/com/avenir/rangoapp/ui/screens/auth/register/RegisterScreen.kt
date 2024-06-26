package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreen(
    state: RegisterState,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onLogin: () -> Unit,
) {
    Scaffold {
        LazyColumn(
            modifier=Modifier.padding(it)
        ){
            item {
                Text(text = "Register")
            }
        }
    }
}