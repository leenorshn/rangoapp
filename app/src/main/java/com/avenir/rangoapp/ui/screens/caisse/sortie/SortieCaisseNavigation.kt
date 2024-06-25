package com.avenir.rangoapp.ui.screens.caisse.sortie

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute


fun NavGraphBuilder.sortieCaisseNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_SORTIE_ROUTE) {
        SortieCaisseScreen(
            onSaveClicked = {}
        )
    }

}