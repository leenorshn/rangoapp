package com.avenir.rangoapp.ui.screens.facture.client

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.clientNavigation(navController: NavController) {
    composable(DestinationRoute.CLIENT_ROUTE) {
        val viewModel: ClientViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        
        // Recharger les clients quand on revient de l'écran de création
        var previousRoute by remember { mutableStateOf<String?>(null) }
        val backStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = backStackEntry?.destination?.route
        
        androidx.compose.runtime.LaunchedEffect(currentRoute) {
            // Recharger quand on revient sur cet écran depuis l'écran de création
            if (currentRoute == DestinationRoute.CLIENT_ROUTE && previousRoute == DestinationRoute.NEW_CLIENT_ROUTE) {
                // Activer le flag pour afficher le message de succès
                viewModel.state.value = viewModel.state.value.copy(showSuccessMessage = true)
                viewModel.onTriggerEvent(ClientEvent.OnRefreshClients)
            }
            previousRoute = currentRoute
        }
        
        ClientScreen(
            state = state,
            onNewClient = {
                navController.navigate(DestinationRoute.NEW_CLIENT_ROUTE)
            }
        )
    }
}