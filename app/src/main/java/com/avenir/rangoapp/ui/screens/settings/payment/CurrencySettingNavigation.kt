package com.avenir.rangoapp.ui.screens.settings.payment

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.currencySettingsNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_CURRENCY_ROUTE) {
        CurrencyScreen()
    }
}