package com.avenir.rangoapp.ui.screens.caisse.transaction

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.transactionNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_SEE_ALL_ROUTE) {
        TransactionScreen()
    }
}