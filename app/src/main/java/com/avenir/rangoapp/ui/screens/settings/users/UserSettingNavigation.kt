package com.avenir.rangoapp.ui.screens.settings.users

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.userSettingNavigation(navController: NavController) {
    composable(DestinationRoute.SETTING_USER_MANAGER_ROUTE) {
        val viewModel = hiltViewModel<UsersViewModel>()
        val state by viewModel.state.collectAsState()
        UsersScreen(
            state = state,
            onNewUserClicked = {
                navController.navigate(DestinationRoute.SETTING_USER_NEW_USER_ROUTE)
            },
            onEvent = viewModel::onTriggerEvent
        )
    }
}