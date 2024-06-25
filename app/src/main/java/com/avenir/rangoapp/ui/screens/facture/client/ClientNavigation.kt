package com.avenir.rangoapp.ui.screens.facture.client

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.clientNavigation(navController: NavController) {
    composable(DestinationRoute.CLIENT_ROUTE) {
        ClientScreen(onNewClient = {
            navController.navigate(DestinationRoute.NEW_CLIENT_ROUTE)
        })
    }
}