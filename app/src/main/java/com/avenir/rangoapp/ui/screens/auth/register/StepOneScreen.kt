package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.components.TextInputWidget


@Composable
fun StepOneScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {

    Scaffold {
        LazyColumn(
            modifier= Modifier.padding(it)
        ){
            item {
                Text(text = "Register 1")
            }
            item {
                TextInputWidget(
                    value = state.name,
                    onValueChange = {
                        onEvent(RegisterEvent.NameChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Votre phone"
                    
                )
                TextInputWidget(
                    value = state.type,
                    onValueChange = {
                        onEvent(RegisterEvent.TypeChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Type d'entreprise"

                )

                TextInputWidget(
                    value = state.city,
                    onValueChange = {
                        onEvent(RegisterEvent.CityChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Type d'entreprise"

                )
                TextInputWidget(
                    value = state.address,
                    onValueChange = {
                        onEvent(RegisterEvent.AddressChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Adresse/numero"

                )
                
            }
            item {
                Row {
                    ElevatedButton(onClick = onPrevious) {
                        Text(text = "Precedent")
                    }
                    PrimaryButton(label = "Suivant") {
                        
                    }
                }
            }
        }
    }

}

