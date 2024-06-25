package com.avenir.rangoapp.ui.screens.settings.about

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.helpNavigation() {
    composable(DestinationRoute.SETTING_HELP_ROUTE) {
        HelpScreen()
    }
}