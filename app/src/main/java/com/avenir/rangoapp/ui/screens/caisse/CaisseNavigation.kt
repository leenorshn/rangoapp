package com.avenir.rangoapp.ui.screens.caisse

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.caisseNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_ROUTE) {
        val viewModel: CaisseViewModel = hiltViewModel()
        
        CaisseScreen(
            viewModel = viewModel,
            onEnterClicked = {
                navController.navigate(DestinationRoute.CAISSE_ENTER_ROUTE)
            },
            onSortieClicked = {
                navController.navigate(DestinationRoute.CAISSE_SORTIE_ROUTE)
            },
            onTransferClicked = {
                navController.navigate(DestinationRoute.CAISSE_TRANSFER_ROUTE)
            },
            onAccountClicked = {
                navController.navigate(DestinationRoute.CAISSE_ACCOUNT_ROUTE)
            },
            onSeeAllClicked = {
                navController.navigate(DestinationRoute.CAISSE_SEE_ALL_ROUTE)
            }
        )
    }
}