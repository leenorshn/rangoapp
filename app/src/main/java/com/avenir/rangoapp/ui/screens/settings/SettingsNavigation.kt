package com.avenir.rangoapp.ui.screens.settings

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.settingsNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_ROUTE) {
        val viewModel:SettingViewModel= hiltViewModel()
        val state by viewModel.state.collectAsState()
        SettingScreen(
            state=state,
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