package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.screens.auth.register.account.RegisterState
import com.avenir.rangoapp.ui.theme.GrayColor

@Composable
fun RegisterScreen(
    state: RegisterState,
    onNext: () -> Unit,
    onLogin: () -> Unit,
) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo), contentDescription = "",
                        tint = Color.Yellow,
                        modifier = Modifier.size(72.dp)
                    )
                    Text(
                        text = "Rango",
                        fontSize = 40.sp, color = Color.Yellow, fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LargeSpace()
                    Text(
                        text = "Créer un compte",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    SmallSpace()
                    Text(
                        "Le processus de creation de votre compte Rango est simple et rapide",
                        color = GrayColor, fontSize = 24.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W300,
                    )
                    SmallSpace()
                    Text(
                        "Veillez suivre les 4 etapes qui vous ouvre un compte Rango",
                        color = GrayColor, fontSize = 18.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W300,
                    )


                }
            }

            item {
                Image(
                    painter = painterResource(id = R.drawable.bg),
                    contentDescription = "",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp)
                )
            }

            item {
                PrimaryButton(
                    label = "Commencer",
                    onClick = onNext
                )
            }
        }
    }
}