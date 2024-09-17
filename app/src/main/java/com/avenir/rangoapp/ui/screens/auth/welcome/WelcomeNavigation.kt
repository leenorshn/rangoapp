package com.avenir.rangoapp.ui.screens.auth.welcome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.welcomeNavigation(navController: NavController) {
    composable(DestinationRoute.WELCOME_ROUTE) {
        WelcomeScreen(
            onLoginClicked = {
                navController.navigate(DestinationRoute.LOGIN_ROUTE)
            },
            onRegisterClicked = {
                navController.navigate(DestinationRoute.REGISTER_STEP_ONE_ROUTE)
            }

        )
    }

}