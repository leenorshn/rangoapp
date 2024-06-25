package com.avenir.rangoapp.ui.screens.settings.cloud_storage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.cloudStorageNavigation(navController: NavController) {
    composable(DestinationRoute.CLOUD_STORAGE_ROUTE) {
        CloudStorageScreen()
    }

}