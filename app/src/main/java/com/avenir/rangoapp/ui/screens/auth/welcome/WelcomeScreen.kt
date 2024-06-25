package com.avenir.rangoapp.ui.screens.auth.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.SmallSpace

@Composable
fun WelcomeScreen(
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onGuestClicked: () -> Unit,
) {
    Scaffold {
        Column(modifier=Modifier.padding(it)) {

            Icon(painter = painterResource(id = R.drawable.logo), contentDescription = "")

            Text(text = "Bienvenue sur Rango")
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(onClick = onRegisterClicked) {
                ListItem(headlineContent = {
                    Text(text = "Creer compte")
                })
            }
            SmallSpace()
            OutlinedCard(onClick = onLoginClicked) {
                ListItem(headlineContent = {
                    Text(text = "Connectez-vous")
                })
            }
            SmallSpace()
            OutlinedCard(onClick = onGuestClicked) {
                ListItem(headlineContent = {
                    Text(text = "Tester comme invité")
                })
            }
        }
    }
}