package com.avenir.rangoapp.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace


@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onBackClick:()->Unit,
) {

    Scaffold {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Rango App",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Yellow,
            )
            SmallSpace()
            Text(text = "Connectez-vous pour continuer", color = Color.Gray)
            LargeSpace()
            LargeSpace()
            TextField(
                value = state.phone,
                onValueChange = {
                    onEvent(LoginEvent.OnPhoneChange(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(
                        RoundedCornerShape(16)
                    ),
                placeholder = {
                    Text(text = "Phone")
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Phone, contentDescription = "")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                ),
            )
            SmallSpace()
            TextField(value = state.password, onValueChange = {
                onEvent(LoginEvent.OnPasswordChange(it))
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(
                        RoundedCornerShape(16)
                    ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                ),
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "")
                },
                placeholder = {
                    Text(text = "Ex:1234")
                })

            LargeSpace()
            LargeSpace()
            LargeSpace()
            ElevatedButton(
                onClick = {
                    onEvent(LoginEvent.OnLogin)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    containerColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
                Text(text = "Connexion")
            }
            Spacer(modifier = Modifier.height(32.dp))
            TextButton(
                onClick = {
                    onBackClick()
                }, colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Cyan,
                    containerColor = Color.Transparent
                )
            ) {
                Text(text = "N'avez pas de compte ?")
            }
        }
    }
}