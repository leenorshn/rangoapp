package com.avenir.rangoapp.ui.screens.stock.provider.newprovider

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.newProviderNavigation(navController: NavController) {
    composable(DestinationRoute.NEW_PROVIDER_ROUTE) {
        NewProviderScreen(
            onSaveClicked = {}
        )
    }
}