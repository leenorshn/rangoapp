package com.avenir.rangoapp.ui.screens.auth.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace

@Composable
fun WelcomeScreen(
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,

) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(
                painter = painterResource(id = R.drawable.logo), contentDescription = "",
                modifier = Modifier.size(72.dp),
                tint = Color.Yellow,
            )


            Text(
                text = "Rango",
                color = Color.Yellow,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            LargeSpace()
            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W300,
                    )
                ) {
                    append("Bienvenu encore")
                }
            }

            Text(text = text)
            SmallSpace()
            Text(
                text = "Gerer, controler, organiser votre business grace a Rango, commencer par créer un compte",
                color = Color.Gray, fontSize = 18.sp, fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(80.dp))

            OutlinedCard(
                onClick = onRegisterClicked) {
                Row (modifier = Modifier.fillMaxWidth().padding(vertical = 28.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,){
                    Icon(
                        painter = painterResource(id = R.drawable.ajouter_24),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Créer un compte", fontWeight = FontWeight.SemiBold,)
                }
            }
            LargeSpace()
            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                onClick = onLoginClicked) {
                Row (modifier = Modifier.fillMaxWidth().padding(vertical = 28.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,){
                    Icon(
                        painter = painterResource(id = R.drawable.bloquer_24),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Connectez-vous", fontWeight = FontWeight.SemiBold,)
                }


            }
            SmallSpace()

        }
    }
}