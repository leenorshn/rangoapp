package com.avenir.rangoapp.ui.screens.caisse.transaction

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.transactionNavigation(navController: NavController) {
    composable(DestinationRoute.CAISSE_SEE_ALL_ROUTE) {
        val viewModel: TransactionCaisseViewModel = hiltViewModel()
        
        TransactionScreen(viewModel = viewModel)
    }
}