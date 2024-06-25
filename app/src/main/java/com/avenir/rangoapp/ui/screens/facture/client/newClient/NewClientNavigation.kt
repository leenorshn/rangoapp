package com.avenir.rangoapp.ui.screens.facture.client.newClient

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.newClientNavigation(navController: NavController){
    composable(DestinationRoute.NEW_CLIENT_ROUTE) {
        NewClientScreen(onSaveClicked = {})
    }
}