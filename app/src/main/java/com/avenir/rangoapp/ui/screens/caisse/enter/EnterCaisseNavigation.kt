package com.avenir.rangoapp.ui.screens.caisse.enter

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.enterCaisseNavigation(navController: NavController) {

    composable(DestinationRoute.CAISSE_ENTER_ROUTE) {
        EnterScreen(
            onSaveClicked = {}
        )
    }

}