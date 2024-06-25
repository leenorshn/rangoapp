package com.avenir.rangoapp.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.SettingMenuItem
import com.avenir.rangoapp.ui.components.UserProfileItem
import com.avenir.rangoapp.ui.screens.settings.users.listOfUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onShopClicked:()->Unit,
    onUsersClicked:()->Unit,
    onCurrencyClicked:()->Unit,
    onHelpClicked:()->Unit,
    onPaymentClicked:()->Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Settings")
            })
        }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .padding(horizontal = 16.dp)) {
            item {
                UserProfileItem(
                    userModel = listOfUser[0]
                )
            }
            item {
                LargeSpace()
                LargeSpace()
               Row (modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceBetween){
                   SettingMenuItem(
                       modifier = Modifier.weight(1f),
                       name = "Shop settings",
                       icon = painterResource(id = R.drawable.des_d6_24),
                       onMenuClicked = {
                           onShopClicked()
                       })
                   Spacer(modifier = Modifier.width(10.dp))
                   SettingMenuItem(
                       modifier = Modifier.weight(1f),
                       name = "Taux d'echange",
                       icon = painterResource(id = R.drawable.fleches_repetition_24),
                       onMenuClicked = {
                           onCurrencyClicked()
                       })

               }
                SmallSpace()
               Row {
                   SettingMenuItem(
                       modifier = Modifier.weight(1f),
                       name = "Utilisateurs",
                       icon = painterResource(id = R.drawable.utilisateurs_24),
                       onMenuClicked = {
                           onUsersClicked()
                       })
                   Spacer(modifier = Modifier.width(10.dp))
                   SettingMenuItem(
                       modifier = Modifier.weight(1f),
                       name = "Payment",
                       icon = painterResource(id = R.drawable.usd_cercle_24),
                       onMenuClicked = {
                           onPaymentClicked()
                       })
               }
                SmallSpace()
                Row {
                    SettingMenuItem(
                        modifier = Modifier.weight(1f),
                        name = "Stocage cloud",
                        icon = painterResource(id = R.drawable.cloud),
                        onMenuClicked = {
                            onPaymentClicked()
                        })
                    Spacer(modifier = Modifier.width(10.dp))
                    SettingMenuItem(
                        modifier = Modifier.weight(1f),
                        name = "Aide",
                        icon = painterResource(id = R.drawable.aide),
                        onMenuClicked = {
                            onHelpClicked()
                        })
                }
            }

            item {
                140.dp.Space()
            }
        }
    }
}