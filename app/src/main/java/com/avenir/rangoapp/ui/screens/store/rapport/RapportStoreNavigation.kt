package com.avenir.rangoapp.ui.screens.store.rapport

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.rapportStoreNavigation() {
    composable(DestinationRoute.RAPPORT_STORE_ROUTE) {
        RapportStoreScreen(
            onProviderClicked = {}
        )
    }
}