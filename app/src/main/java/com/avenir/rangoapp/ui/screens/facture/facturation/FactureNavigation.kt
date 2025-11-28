package com.avenir.rangoapp.ui.screens.facture.facturation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.factureNavigation(navController: NavController){
    composable(DestinationRoute.FACTURATION_ROUTE) {
        val viewModel: FactureViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        
        // Load factures when screen is first displayed
        LaunchedEffect(Unit) {
            viewModel.onTriggerEvent(FactureEvent.OnFactureLoaded)
        }
        
        FacturationScreen(
            state = state,
            onEvent = viewModel::onTriggerEvent,
            onClientClicked = {
                navController.navigate(DestinationRoute.CLIENT_ROUTE)
            },
            onNewFactureClicked = {
                navController.navigate(DestinationRoute.NEW_FACTURE_ROUTE)
            }
        )
    }
}