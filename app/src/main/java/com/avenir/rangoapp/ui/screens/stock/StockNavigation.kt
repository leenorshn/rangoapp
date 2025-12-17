package com.avenir.rangoapp.ui.screens.stock

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.avenir.rangoapp.core.DestinationRoute

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.storeNavigation(navController: NavController) {
    composable(DestinationRoute.STORE_ROUTE) {
        val viewModel:StockViewModel= hiltViewModel()
        val state by viewModel.state.collectAsState()
        
        // Recharger les produits quand on revient de l'écran de création
        var previousRoute by remember { mutableStateOf<String?>(null) }
        val backStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = backStackEntry?.destination?.route
        
        androidx.compose.runtime.LaunchedEffect(currentRoute) {
            // Recharger quand on revient sur cet écran depuis l'écran de création
            if (currentRoute == DestinationRoute.STORE_ROUTE && previousRoute == DestinationRoute.NEW_PRODUCT_ROUTE) {
                // Activer le flag pour afficher le message de succès
                viewModel.state.value = viewModel.state.value.copy(showSuccessMessage = true)
                viewModel.onTriggerEvent(StoreEvent.OnRefreshProducts)
            }
            previousRoute = currentRoute
        }
        
        StoreScreen(
            state = state,
            onAddNewProductClicked = {
                navController.navigate(DestinationRoute.NEW_PRODUCT_ROUTE)
            },
            onRapportStoreClicked = {
                navController.navigate(DestinationRoute.RAPPORT_STORE_ROUTE)
            }
        )
    }
}