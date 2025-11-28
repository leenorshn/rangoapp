package com.avenir.rangoapp.ui.screens.facture.client.newClient

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.newClientNavigation(navController: NavController) {
    composable(DestinationRoute.NEW_CLIENT_ROUTE) {
        val viewModel: NewClientViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        
        NewClientScreen(
            state = state,
            onEvent = { viewModel.onTriggerEvent(it) },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}