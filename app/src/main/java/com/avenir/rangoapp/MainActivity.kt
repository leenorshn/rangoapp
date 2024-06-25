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
import com.avenir.rangoapp.ui.screens.auth.login.LoginScreen
import com.avenir.rangoapp.ui.screens.auth.login.LoginViewModel
import com.avenir.rangoapp.ui.screens.caisse.CaisseScreen
import com.avenir.rangoapp.ui.screens.caisse.account.AccountCaisseScreen
import com.avenir.rangoapp.ui.screens.caisse.enter.EnterScreen
import com.avenir.rangoapp.ui.screens.caisse.sortie.SortieCaisseScreen
import com.avenir.rangoapp.ui.screens.caisse.transaction.TransactionScreen
import com.avenir.rangoapp.ui.screens.caisse.transfer.TransferCaisseScreen
import com.avenir.rangoapp.ui.screens.facture.facturation.newfacture.NewFactureScreen
import com.avenir.rangoapp.ui.screens.home.HomeScreen
import com.avenir.rangoapp.ui.screens.home.HomeState
import com.avenir.rangoapp.ui.screens.settings.SettingScreen
import com.avenir.rangoapp.ui.screens.settings.about.HelpScreen
import com.avenir.rangoapp.ui.screens.settings.payment.CurrencyScreen
import com.avenir.rangoapp.ui.screens.settings.payment.PaymentScreen
import com.avenir.rangoapp.ui.screens.settings.shop.ShopSettingsScreen
import com.avenir.rangoapp.ui.screens.settings.users.UsersScreen
import com.avenir.rangoapp.ui.screens.settings.users.newuser.NewUserScreen
import com.avenir.rangoapp.ui.screens.store.StoreScreen
import com.avenir.rangoapp.ui.screens.store.newproduct.NewProductScreen
import com.avenir.rangoapp.ui.screens.store.provider.ProviderScreen
import com.avenir.rangoapp.ui.screens.store.rapport.RapportStoreScreen
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









                    composable(DestinationRoute.NEW_PRODUCT_ROUTE) {
                        NewProductScreen(
                            onSaveClicked = {}
                        )
                    }











                    composable(DestinationRoute.SETTING_ROUTE) {
                        SettingScreen(
                            onShopClicked = {
                                navController.navigate(DestinationRoute.SETTING_SHOP_ROUTE)
                            },
                            onUsersClicked = {
                                navController.navigate(DestinationRoute.SETTING_USER_MANAGER_ROUTE)
                            },
                            onCurrencyClicked = {
                                navController.navigate(DestinationRoute.SETTING_CURRENCY_ROUTE)
                            },
                            onPaymentClicked = {
                                navController.navigate(DestinationRoute.SETTING_PAYMENT_ROUTE)
                            },
                            onHelpClicked = {
                                navController.navigate(DestinationRoute.SETTING_HELP_ROUTE)
                            }
                        )
                    }
                    composable(DestinationRoute.SETTING_PAYMENT_ROUTE) {
                        PaymentScreen()
                    }
                    composable(DestinationRoute.SETTING_CURRENCY_ROUTE) {
                        CurrencyScreen()
                    }
                    composable(DestinationRoute.SETTING_HELP_ROUTE) {
                        HelpScreen()
                    }
                    composable(DestinationRoute.SETTING_SHOP_ROUTE) {
                        ShopSettingsScreen()
                    }

                    composable(DestinationRoute.SETTING_USER_MANAGER_ROUTE) {
                        UsersScreen(
                            onNewUserClicked = {
                                navController.navigate(DestinationRoute.SETTING_USER_NEW_USER_ROUTE)
                            }
                        )
                    }
                    composable(DestinationRoute.SETTING_USER_NEW_USER_ROUTE) {
                        NewUserScreen(
                            onSaveClicked = {}
                        )
                    }
                }

                // }
            }
        }
    }
}




