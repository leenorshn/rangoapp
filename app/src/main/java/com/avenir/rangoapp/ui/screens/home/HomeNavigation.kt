package com.avenir.rangoapp.ui.screens.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute


fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable(DestinationRoute.HOME_ROUTE) {
        val viewModel:HomeViewModel= hiltViewModel()
        val viewState by viewModel.currentUser.collectAsState()

        println(viewState)
        HomeScreen(
            state = viewState,
            event = viewModel::onTriggerEvent,
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