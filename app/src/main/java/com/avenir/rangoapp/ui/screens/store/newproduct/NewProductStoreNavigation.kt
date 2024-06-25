package com.avenir.rangoapp.ui.screens.store.newproduct

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.newProductStoreNavigation(navController: NavController) {
    composable(DestinationRoute.NEW_PRODUCT_ROUTE) {
        NewProductScreen(
            onSaveClicked = {}
        )
    }
}