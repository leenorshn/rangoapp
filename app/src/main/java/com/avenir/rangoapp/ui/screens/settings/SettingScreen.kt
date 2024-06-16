package com.avenir.rangoapp.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.data.models.UserModel
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
                SettingMenuItem(
                    name = "Shop setting",
                    icon = painterResource(id = R.drawable.mallette_24),
                    onMenuClicked = {
                        onShopClicked()
                    })
                SmallSpace()
                SettingMenuItem(
                    name = "Gerer les utilisateurs",
                    icon = painterResource(id = R.drawable.utilisateurs_24),
                    onMenuClicked = {
                        onUsersClicked()
                    })
                SmallSpace()
                SettingMenuItem(
                    name = "Taux d'echange",
                    icon = painterResource(id = R.drawable.dollar_24),
                    onMenuClicked = {
                        onCurrencyClicked()
                    })
                SmallSpace()
                SettingMenuItem(
                    name = "Payment",
                    icon = painterResource(id = R.drawable.statistiques_24),
                    onMenuClicked = {
                        onPaymentClicked()
                    })
                SmallSpace()
                SettingMenuItem(
                    name = "Stocage cloud",
                    icon = painterResource(id = R.drawable.time),
                    onMenuClicked = {
                        onPaymentClicked()
                    })
                SmallSpace()
                SettingMenuItem(
                    name = "Aide",
                    icon = painterResource(id = R.drawable.ic_left_arrow),
                    onMenuClicked = {
                        onHelpClicked()
                    })
            }
        }
    }
}