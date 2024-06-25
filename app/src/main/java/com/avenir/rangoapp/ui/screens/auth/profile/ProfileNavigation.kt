package com.avenir.rangoapp.ui.screens.auth.profile

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.profileNavGraph(navController: NavController) {
    composable(DestinationRoute.PROFILE_ROUTE) {
        val profileViewModel: ProfileViewModel = hiltViewModel()
        ProfileScreen(
            onEvent = profileViewModel::onTriggerEvent,
        )
    }
}