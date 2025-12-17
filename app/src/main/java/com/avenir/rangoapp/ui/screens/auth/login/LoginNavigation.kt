package com.avenir.rangoapp.ui.screens.auth.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.MainViewModel
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable(DestinationRoute.LOGIN_ROUTE) {
        val viewModel: LoginViewModel = hiltViewModel()
        val mainViewModel: MainViewModel = hiltViewModel()
        
        LoginScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            onBackClick = {
                navController.navigate(DestinationRoute.WELCOME_ROUTE)
            },
            navigateToHome = {
                // Rafraîchir l'état d'authentification dans MainViewModel
                mainViewModel.refreshAuthState()
                // Naviguer vers la page principale
                navController.navigate(DestinationRoute.MAIN_NAV_ROUTE) {
                    // Supprimer toutes les routes d'authentification de la pile
                    popUpTo(DestinationRoute.AUTH_ROUTE) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}