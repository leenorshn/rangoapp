package com.avenir.rangoapp.ui.screens.settings.shop

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.shopSettingsNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_SHOP_ROUTE) {
        ShopSettingsScreen()
    }
}