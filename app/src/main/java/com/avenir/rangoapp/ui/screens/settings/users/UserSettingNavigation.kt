package com.avenir.rangoapp.ui.screens.settings.users

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.userSettingNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_USER_MANAGER_ROUTE) {
        UsersScreen(
            onNewUserClicked = {
                navController.navigate(DestinationRoute.SETTING_USER_NEW_USER_ROUTE)
            }
        )
    }
}