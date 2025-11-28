package com.avenir.rangoapp.ui.screens.stock.rapport

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.rapportStoreNavigation() {
    composable(DestinationRoute.RAPPORT_STORE_ROUTE) {
        val viewModel:RapportStockViewModel= hiltViewModel()
        val state by viewModel.state.collectAsState()
        RapportStoreScreen(
            state = state,
           // onProviderClicked = {}
        )
    }
}