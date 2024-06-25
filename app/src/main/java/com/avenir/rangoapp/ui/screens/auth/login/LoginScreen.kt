package com.avenir.rangoapp.ui.screens.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace


@Composable
fun LoginScreen(
    state: LoginState,
    onLogin: () -> Unit
) {

    Scaffold {
        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding()
            )
        ) {
            Text(text = "Login")
            LargeSpace()
            TextField(value = state.phone, onValueChange = {
                state.phone = it
            },
                placeholder = {
                    Text(text = "Phone")
                })
            SmallSpace()
            TextField(value = state.password, onValueChange = {
                state.password = it
            }, placeholder = {
                Text(text = "Ex:1234")
            })
            SmallSpace()
            Text(text = "Forgot Password?")
            LargeSpace()
            ElevatedButton(onClick = {
                onLogin()
            }) {
                Text(text = "Connexion")
            }
        }
    }
}