package com.avenir.rangoapp.ui.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute


fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable(DestinationRoute.MAIN_NAV_ROUTE) {
        HomeScreen(
            state = HomeState(user = null),
            onFactureClicked = {
                navController.navigate(DestinationRoute.FACTURATION_ROUTE) {

                }
            },
            onProfileClicked = {
                navController.navigate(DestinationRoute.PROFILE_ROUTE)
            },
            onSettingClicked = {
                navController.navigate(DestinationRoute.SETTING_ROUTE)
            },
            onStoreClicked = {
                navController.navigate(DestinationRoute.STORE_ROUTE)
            },
            onCaisseClicked = {
                navController.navigate(DestinationRoute.CAISSE_ROUTE)
            },
        )
    }
}