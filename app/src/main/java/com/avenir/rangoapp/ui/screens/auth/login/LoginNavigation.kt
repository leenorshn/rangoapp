package com.avenir.rangoapp.ui.screens.auth.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable(DestinationRoute.LOGIN_ROUTE) {
        val viewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            onBackClick = {
                navController.navigate(DestinationRoute.REGISTER_ROUTE)
            })
    }
}