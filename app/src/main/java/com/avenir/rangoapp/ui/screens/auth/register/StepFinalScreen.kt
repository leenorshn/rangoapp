package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StepFinalScreen(
    state: RegisterState,
    onSubmit: () -> Unit,
    onPrevious: () -> Unit,
) {
    Scaffold {
        LazyColumn(
            modifier= Modifier.padding(it)
        ){
            item {
                Text(text = "Register")
            }
            item {
                Row {
                    ElevatedButton(onClick = onPrevious) {
                        Text(text = "Precedent")
                    }
                    ElevatedButton(onClick = onSubmit) {
                        Text(text = "Precedent")
                    }
                }
            }
        }
    }
}