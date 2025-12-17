package com.avenir.rangoapp.ui.screens.caisse.sortie

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.sortieCaisseNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_SORTIE_ROUTE) {
        val viewModel: SortieCaisseViewModel = hiltViewModel()
        
        SortieCaisseScreen(
            viewModel = viewModel,
            onSaveClicked = {
                navController.popBackStack()
            }
        )
    }
}