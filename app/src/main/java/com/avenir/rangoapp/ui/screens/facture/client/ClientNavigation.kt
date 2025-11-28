package com.avenir.rangoapp.ui.screens.facture.client

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.clientNavigation(navController: NavController) {
    composable(DestinationRoute.CLIENT_ROUTE) {
        val viewModel: ClientViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        
        ClientScreen(
            state = state,
            onNewClient = {
                navController.navigate(DestinationRoute.NEW_CLIENT_ROUTE)
            }
        )
    }
}