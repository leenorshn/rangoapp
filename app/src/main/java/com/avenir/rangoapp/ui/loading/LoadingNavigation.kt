package com.avenir.rangoapp.ui.loading

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.loadingScreenNavigation() {
    composable(DestinationRoute.LOADING_SCREEN_ROUTE) {
        LoadingScreen()
    }

}