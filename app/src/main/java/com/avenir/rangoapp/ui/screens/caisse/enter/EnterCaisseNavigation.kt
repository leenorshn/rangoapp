package com.avenir.rangoapp.ui.screens.caisse.enter

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.enterCaisseNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_ENTER_ROUTE) {
        val viewModel: EnterCaisseViewModel = hiltViewModel()
        
        EnterScreen(
            viewModel = viewModel,
            onSaveClicked = {
                navController.popBackStack()
            }
        )
    }
}