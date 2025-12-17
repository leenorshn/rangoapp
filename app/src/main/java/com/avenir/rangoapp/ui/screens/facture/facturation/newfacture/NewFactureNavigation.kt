package com.avenir.rangoapp.ui.screens.facture.facturation.newfacture

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.newFactureNavigation(navController: NavController) {
    composable(DestinationRoute.NEW_FACTURE_ROUTE) {
        val factureViewModel: NewFactureViewModel = hiltViewModel()
        val factureState by factureViewModel.state.collectAsState()
        
        NewFactureScreen(
            state = factureState,
            onEvent = { factureViewModel.onTriggerEvent(it) },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}