package com.avenir.rangoapp.ui.screens.caisse.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.accountCaisseNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_ACCOUNT_ROUTE) {
        AccountCaisseScreen()
    }
}