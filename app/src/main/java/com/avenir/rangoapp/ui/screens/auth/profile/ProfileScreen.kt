package com.avenir.rangoapp.ui.screens.auth.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.UserProfileItem
import com.avenir.rangoapp.ui.screens.settings.users.listOfUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onEvent:(ProfileEvent)->Unit,
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
                140.dp.Space()
            }
            item {
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    onClick = {
                        onEvent(ProfileEvent.OnLogout)
                    }) {
                   Text(text = "Logout")
                }
            }
        }
    }
}