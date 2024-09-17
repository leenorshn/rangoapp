package com.avenir.rangoapp.ui.screens.store

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute
import com.avenir.rangoapp.ui.screens.auth.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.storeNavigation(navController: NavController) {
    composable(DestinationRoute.STORE_ROUTE) {
        val viewModel:StoreViewModel= hiltViewModel()
        val state by viewModel.state.collectAsState()
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