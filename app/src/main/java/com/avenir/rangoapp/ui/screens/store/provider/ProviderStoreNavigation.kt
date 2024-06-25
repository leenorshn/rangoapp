package com.avenir.rangoapp.ui.screens.store.provider

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.providerStoreNavigation(navController: NavController) {
    composable(DestinationRoute.PROVIDER_ROUTE) {
        ProviderScreen(
            onNewProviderClicked = {}
        )
    }
}