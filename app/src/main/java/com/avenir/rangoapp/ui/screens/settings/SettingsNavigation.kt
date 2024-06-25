package com.avenir.rangoapp.ui.screens.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.settingsNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_ROUTE) {
        SettingScreen(
            onShopClicked = {
                navController.navigate(DestinationRoute.SETTING_SHOP_ROUTE)
            },
            onUsersClicked = {
                navController.navigate(DestinationRoute.SETTING_USER_MANAGER_ROUTE)
            },
            onCurrencyClicked = {
                navController.navigate(DestinationRoute.SETTING_CURRENCY_ROUTE)
            },
            onPaymentClicked = {
                navController.navigate(DestinationRoute.SETTING_PAYMENT_ROUTE)
            },
            onHelpClicked = {
                navController.navigate(DestinationRoute.SETTING_HELP_ROUTE)
            }
        )
    }
}