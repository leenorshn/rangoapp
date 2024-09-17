package com.avenir.rangoapp.ui.screens.store.newproduct

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.newProductStoreNavigation(navController: NavController) {
    composable(DestinationRoute.NEW_PRODUCT_ROUTE) {
        val viewModel:NewProductViewModel= hiltViewModel()
        val state by viewModel.state.collectAsState()
        NewProductScreen(
            state = state,
            onEvent = viewModel::onTriggerEvent,
            navigateToProducts = {
                navController.navigateUp()
            }
        )
    }
}