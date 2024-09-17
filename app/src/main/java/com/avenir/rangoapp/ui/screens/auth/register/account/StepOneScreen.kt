package com.avenir.rangoapp.ui.screens.auth.register.account

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.components.TextInputWidget


@Composable
fun StepOneScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    navigateToCompanyCreation: () -> Unit,
) {

    LaunchedEffect(key1 = state.isSuccess) {
       if (state.isSuccess) {
           navigateToCompanyCreation()
       }
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
                    painter = painterResource(id = R.drawable.logo), contentDescription = "",
                    modifier = Modifier.size(72.dp),
                    tint = Color.Yellow,
                )
                Text(text = "Création de compte", fontSize = 32.sp, fontWeight = FontWeight.W300)
                if (state.error!=null){
                    Text(text = "${state.error}", color = Color.Red)
                }
            }
            item {
                LargeSpace()
                TextInputWidget(
                    value = state.username,
                    onValueChange = {
                        onEvent(RegisterEvent.NameChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    ),
                    placeholder = {
                        Text(text = "Entrer votre email")
                    },
                    label = "Votre gmail",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        if (state.username.length in 10..13) Icon(
                            painter = painterResource(id = R.drawable.verified_icon),
                            contentDescription = "",
                            tint = Color.Green)
                    },
                    supportingText = {
                        Text("Votre address email (gmail)", fontSize = 12.sp,color=Color.Gray)
                    }

                )


                SmallSpace()
                TextInputWidget(
                    value = state.password,
                    onValueChange = {
                        onEvent(RegisterEvent.PasswordChanged(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Mot de passe ",
                    placeholder = {
                        Text(text = "Mot de passe")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        if (state.isTwoPasswordValid) Icon(
                            painter = painterResource(id = R.drawable.verified_icon),
                            contentDescription = "",
                            tint = Color.Green)
                    },
                    supportingText = {
                        Text("Minimum 8 caractères", fontSize = 12.sp,color=Color.Gray)
                    }

                )

                SmallSpace()
                TextInputWidget(
                    value = state.confirmPassword,
                    onValueChange = {
                        onEvent(RegisterEvent.ConfirmPasswordChanged(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Confirmer mot de passe ",
                    placeholder = {
                        Text(text = "Confirmer mot de passe")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        if (state.isTwoPasswordValid) Icon(
                            painter = painterResource(id = R.drawable.verified_icon),
                            contentDescription = "",
                            tint = Color.Green)
                    },
                    supportingText = {
                        Text("Minimum 8 caractères", fontSize = 12.sp,color=Color.Gray)
                    }

                )
                SmallSpace()
                if(!state.isTwoPasswordValid){
                    Text(text = state.error ?: "", color = MaterialTheme.colorScheme.error)
                }

            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.Yellow)
                    SmallSpace()
                }else {

                    PrimaryButton(label = "Créer compte") {
                        onEvent(RegisterEvent.Submit)
                    }
                }

            }
        }
    }

}

