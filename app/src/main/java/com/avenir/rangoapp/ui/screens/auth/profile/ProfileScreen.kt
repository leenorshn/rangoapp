package com.avenir.rangoapp.ui.screens.auth.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.UserProfileItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: UserState,
    loggedState: LoggedState,
    navigateToLogin: () -> Unit,
    onEvent:(ProfileEvent)->Unit,
) {

    LaunchedEffect(key1 = loggedState.isLogged) {
        if (loggedState.isLogged){
            navigateToLogin()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Profile")
            })
        }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .padding(horizontal = 16.dp)) {
            item {
                if (state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Yellow,
                    )
                }
                if (state.error != null) {
                    Text(text = "Error de chargement", color = Color.Red)
                }
            }
            item {
                state.user?.let { it1 ->
                    UserProfileItem(
                        userModel = it1
                    )
                }
            }


            item {
                140.dp.Space()
            }
            item {

                ElevatedButton(
                    enabled = !loggedState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    onClick = {
                        onEvent(ProfileEvent.OnLogout)
                    }) {
                   Text(text = if (loggedState.isLoading) "Loading..." else "Logout")
                }
            }
        }
    }
}