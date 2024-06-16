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
import com.avenir.RangoApp.R
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.ui.components.SettingMenuItem
import com.avenir.rangoapp.ui.components.UserProfileItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Settings")
            })
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                UserProfileItem(
                    userModel = UserModel(
                        id = "",
                        name = "Victor Sh",
                        phone = "+243 978 154 329"
                    )
                )
            }
            item {
                SettingMenuItem(
                    name = "Shop setting",
                    icon = painterResource(id = R.drawable.mallette_24),
                    onMenuClicked = {})
                SettingMenuItem(
                    name = "Utilisateurs",
                    icon = painterResource(id = R.drawable.utilisateurs_24),
                    onMenuClicked = {})
                SettingMenuItem(
                    name = "Gerer le Taux",
                    icon = painterResource(id = R.drawable.dollar_24),
                    onMenuClicked = {})
                SettingMenuItem(
                    name = "Payment",
                    icon = painterResource(id = R.drawable.statistiques_24),
                    onMenuClicked = {})
                SettingMenuItem(
                    name = "Aide",
                    icon = painterResource(id = R.drawable.ic_left_arrow),
                    onMenuClicked = {})
            }
        }
    }
}