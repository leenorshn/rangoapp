package com.avenir.rangoapp.ui.screens.caisse.transfer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.transferNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_TRANSFER_ROUTE) {
        TransferCaisseScreen(
            onSaveClicked = {}
        )
    }
}