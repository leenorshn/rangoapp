package com.avenir.rangoapp.ui.screens.facture.facturation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.factureNavigation(navController: NavController){
    composable(DestinationRoute.FACTURATION_ROUTE) {
        FacturationScreen(
            onClientClicked = {
                navController.navigate(DestinationRoute.CLIENT_ROUTE)
            },
            onNewFactureClicked = {
                navController.navigate(DestinationRoute.NEW_FACTURE_ROUTE)
            }
        )
    }
}