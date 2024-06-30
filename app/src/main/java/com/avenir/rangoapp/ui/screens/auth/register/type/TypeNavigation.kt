package com.avenir.rangoapp.ui.screens.auth.register.type

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.avenir.rangoapp.core.DestinationRoute

fun NavGraphBuilder.typeNavigation(
    navController: NavHostController
) {
   composable(DestinationRoute.TYPE_ROUTE){
       TypeScreen(
           onTypeSelected = {
               navController.navigate(DestinationRoute.REGISTER_STEP_TWO_ROUTE)
           }
       )
   }
}