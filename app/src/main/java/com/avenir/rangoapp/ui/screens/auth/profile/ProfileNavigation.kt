package com.avenir.rangoapp.ui.screens.auth.profile

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.MainViewModel
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.profileNavGraph(navController: NavController) {
    composable(DestinationRoute.PROFILE_ROUTE) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val mainViewModel: MainViewModel = hiltViewModel()
        val state by profileViewModel.state.collectAsState()
        val loggedState by profileViewModel.loggedState.collectAsState()
        
        ProfileScreen(
            state = state,
            loggedState = loggedState,
            navigateToLogin = {
                // Rafraîchir l'état d'authentification dans MainViewModel
                mainViewModel.refreshAuthState()
                // Naviguer vers la page welcome
                navController.navigate(DestinationRoute.WELCOME_ROUTE) {
                    // Supprimer toutes les routes principales de la pile
                    popUpTo(DestinationRoute.MAIN_NAV_ROUTE) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onEvent = profileViewModel::onTriggerEvent,
        )
    }
}