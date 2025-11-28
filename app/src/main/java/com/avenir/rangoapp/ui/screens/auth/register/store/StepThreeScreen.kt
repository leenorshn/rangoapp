package com.avenir.rangoapp.ui.screens.auth.register.store

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.components.TextInputWidget
import com.avenir.rangoapp.ui.theme.FailureColor

@Composable
fun StepThreeScreen(
    state: StoreState,
    onEvent: (StoreEvent) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    var error = remember {
        mutableStateOf("")
    }
    
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    painter = painterResource(id = R.drawable.logo), 
                    contentDescription = "",
                    modifier = Modifier.size(72.dp),
                    tint = Color.Yellow,
                )
                Text(text = "Informations du magasin", fontSize = 24.sp)
                Text(
                    text = "Créez votre premier magasin",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            item {
                LargeSpace()
                TextInputWidget(
                    value = state.name,
                    onValueChange = {
                        onEvent(StoreEvent.NameChanged(it))
                    },
                    placeholder = {
                        Text(text = "Nom du magasin")
                    },
                    supportingText = {
                        Text(
                            text = "Exemple: Boutique Centrale",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Nom du magasin",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.store_alt),
                            contentDescription = "",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
                SmallSpace()
                TextInputWidget(
                    value = state.address,
                    onValueChange = {
                        onEvent(StoreEvent.AddressChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Adresse du magasin",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = ""
                        )
                    },
                    placeholder = {
                        Text(text = "Adresse")
                    },
                    supportingText = {
                        Text(
                            text = "Exemple: Butembo/Rue-Kin/GTB N=32",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                )
                SmallSpace()
                TextInputWidget(
                    value = state.phone,
                    onValueChange = {
                        onEvent(StoreEvent.PhoneChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Téléphone du magasin",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Phone,
                            contentDescription = ""
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    placeholder = {
                        Text(text = "Téléphone")
                    },
                    supportingText = {
                        Text(
                            text = "Numéro de contact du magasin",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                )
            }
            item {
                if (state.error != null) {
                    Text(
                        text = state.error,
                        color = FailureColor,
                        fontSize = 12.sp
                    )
                }
                if (error.value.isNotEmpty()) {
                    Text(
                        text = error.value,
                        color = FailureColor,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                if (state.isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(color = Color.Yellow)
                    SmallSpace()
                } else {
                    PrimaryButton(label = "Terminer") {
                        if (state.name.length > 2 && state.address.length > 6 && state.phone.length > 8) {
                            onEvent(StoreEvent.OnSubmit)
                        } else {
                            error.value = "Veuillez remplir tous les champs correctement"
                        }
                    }
                }
            }
        }
    }
}


