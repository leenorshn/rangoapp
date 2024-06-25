package com.avenir.rangoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avenir.rangoapp.core.DestinationRoute
import com.avenir.rangoapp.ui.screens.home.HomeScreen
import com.avenir.rangoapp.ui.screens.home.HomeState
import com.avenir.rangoapp.ui.screens.settings.about.HelpScreen
import com.avenir.rangoapp.ui.screens.settings.payment.CurrencyScreen
import com.avenir.rangoapp.ui.screens.settings.payment.PaymentScreen
import com.avenir.rangoapp.ui.screens.settings.shop.ShopSettingsScreen
import com.avenir.rangoapp.ui.screens.settings.users.UsersScreen
import com.avenir.rangoapp.ui.screens.settings.users.newuser.NewUserScreen
import com.avenir.rangoapp.ui.theme.RangoAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RangoAppTheme {
                // Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val navController = rememberNavController()

                val viewModel: MainViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()





                val startDestination = if (state.isLoggedIn) {
                    DestinationRoute.MAIN_NAV_ROUTE
                } else if (state.isLoading) {
                    DestinationRoute.LOADING_SCREEN_ROUTE
                } else {
                    DestinationRoute.LOGIN_ROUTE
                }


                NavHost(navController = navController, startDestination = startDestination) {



                    composable(DestinationRoute.LOADING_SCREEN_ROUTE) {
                        LoadingScreen()
                    }

                    composable(DestinationRoute.MAIN_NAV_ROUTE) {
                        HomeScreen(
                            state = HomeState(user = null),
                            onFactureClicked = {
                                navController.navigate(DestinationRoute.FACTURATION_ROUTE) {

                                }
                            },
                            onProfileClicked = {
                                navController.navigate(DestinationRoute.PROFILE_ROUTE)
                            },
                            onSettingClicked = {
                                navController.navigate(DestinationRoute.SETTING_ROUTE)
                            },
                            onStoreClicked = {
                                navController.navigate(DestinationRoute.STORE_ROUTE)
                            },
                            onCaisseClicked = {
                                navController.navigate(DestinationRoute.CAISSE_ROUTE)
                            },
                        )
                    }
























                    composable(DestinationRoute.SETTING_HELP_ROUTE) {
                        HelpScreen()
                    }




                }

                // }
            }
        }
    }
}




