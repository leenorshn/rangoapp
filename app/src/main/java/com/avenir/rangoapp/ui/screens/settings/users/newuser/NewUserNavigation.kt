package com.avenir.rangoapp.ui.screens.settings.users.newuser

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.newUserNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_USER_NEW_USER_ROUTE) {
        NewUserScreen(
            onSaveClicked = {}
        )
    }
}