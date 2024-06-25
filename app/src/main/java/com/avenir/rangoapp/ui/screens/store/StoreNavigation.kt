package com.avenir.rangoapp.ui.screens.store

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.storeNavigation(navController: NavController) {
    composable(DestinationRoute.STORE_ROUTE) {
        StoreScreen(
            onAddNewProductClicked = {
                navController.navigate(DestinationRoute.NEW_PRODUCT_ROUTE)
            },
            onRapportStoreClicked = {
                navController.navigate(DestinationRoute.RAPPORT_STORE_ROUTE)
            }
        )
    }
}