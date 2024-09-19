package com.avenir.rangoapp.ui.screens.settings.shop

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.shopSettingsNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_SHOP_ROUTE) {
val viewModel= hiltViewModel<ShopViewModel>()
        val state by viewModel.state.collectAsState()
        ShopSettingsScreen(
            state = state,
            onEvent = viewModel::onTriggerEvent
        )
    }
}