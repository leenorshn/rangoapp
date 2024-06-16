package com.avenir.rangoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avenir.rangoapp.core.DestinationRoute
import com.avenir.rangoapp.ui.screens.caisse.CaisseScreen
import com.avenir.rangoapp.ui.screens.caisse.account.AccountCaisseScreen
import com.avenir.rangoapp.ui.screens.caisse.enter.EnterScreen
import com.avenir.rangoapp.ui.screens.caisse.sortie.SortieCaisseScreen
import com.avenir.rangoapp.ui.screens.caisse.transaction.TransactionScreen
import com.avenir.rangoapp.ui.screens.caisse.transfer.TransferCaisseScreen
import com.avenir.rangoapp.ui.screens.facture.client.ClientScreen
import com.avenir.rangoapp.ui.screens.facture.client.newClient.NewClientScreen
import com.avenir.rangoapp.ui.screens.facture.facturation.FacturationScreen
import com.avenir.rangoapp.ui.screens.facture.facturation.newfacture.NewFactureScreen
import com.avenir.rangoapp.ui.screens.home.HomeScreen
import com.avenir.rangoapp.ui.screens.home.HomeState
import com.avenir.rangoapp.ui.screens.settings.SettingScreen
import com.avenir.rangoapp.ui.screens.store.StoreScreen
import com.avenir.rangoapp.ui.screens.store.product.ProductScreen
import com.avenir.rangoapp.ui.screens.store.product.newproduct.NewProductScreen
import com.avenir.rangoapp.ui.screens.store.product.provider.ProviderScreen
import com.avenir.rangoapp.ui.theme.RangoAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RangoAppTheme {
               // Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val navController= rememberNavController()
                NavHost(navController = navController, startDestination = DestinationRoute.MAIN_NAV_ROUTE) {
                    composable(DestinationRoute.MAIN_NAV_ROUTE){
                        HomeScreen(
                            state = HomeState(user = null),
                            onFactureClicked = {
                                navController.navigate(DestinationRoute.FACTURATION_ROUTE){

                                }
                            },
                            onProfileClicked = {
                                navController.navigate(DestinationRoute.SETTING_ROUTE)
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
                    composable(DestinationRoute.FACTURATION_ROUTE){
                        FacturationScreen(
                            onClientClicked = {
                                navController.navigate(DestinationRoute.CLIENT_ROUTE)
                            },
                            onNewFactureClicked = {
                                navController.navigate(DestinationRoute.NEW_FACTURE_ROUTE)
                            }
                        )
                    }
                    composable(DestinationRoute.CLIENT_ROUTE){
                        ClientScreen(onNewClient = {
                            navController.navigate(DestinationRoute.NEW_CLIENT_ROUTE)
                        })
                    }

                    composable(DestinationRoute.NEW_CLIENT_ROUTE){
                        NewClientScreen(onSaveClicked = {})
                    }

                    composable(DestinationRoute.NEW_FACTURE_ROUTE){
                        NewFactureScreen(

                        )
                    }
                    composable(DestinationRoute.STORE_ROUTE){
                        StoreScreen(
                            onAddNewProductClicked = {
                                navController.navigate(DestinationRoute.ADD_PRODUCT_IN_STORE_ROUTE)
                            }
                        )
                    }
                    composable(DestinationRoute.ADD_PRODUCT_IN_STORE_ROUTE){
                        ProductScreen(
                            onNewProductClicked = {
                               navController.navigate(DestinationRoute.NEW_PRODUCT_ROUTE)
                            },
                            onProviderClicked = {
                                navController.navigate(DestinationRoute.PROVIDER_ROUTE)
                            }
                        )
                    }
                    composable(DestinationRoute.NEW_PRODUCT_ROUTE){
                        NewProductScreen(
                            onSaveClicked = {}
                        )
                    }
                    composable(DestinationRoute.PROVIDER_ROUTE){
                        ProviderScreen(
                            onNewProviderClicked = {}
                        )
                    }

                    composable(DestinationRoute.CAISSE_ROUTE){
                        CaisseScreen(
                            onEnterClicked = {
                                navController.navigate(DestinationRoute.CAISSE_ENTER_ROUTE)
                            },
                            onSortieClicked = {
                                navController.navigate(DestinationRoute.CAISSE_SORTIE_ROUTE)
                            },
                            onTransferClicked = {
                                navController.navigate(DestinationRoute.CAISSE_TRANSFER_ROUTE)
                            },
                            onAccountClicked = {
                                navController.navigate(DestinationRoute.CAISSE_ACCOUNT_ROUTE)
                            },
                            onSeeAllClicked = {
                                navController.navigate(DestinationRoute.CAISSE_SEE_ALL_ROUTE)
                            }
                        )
                    }

                    composable(DestinationRoute.CAISSE_ENTER_ROUTE){
                        EnterScreen(
                            onSaveClicked = {}
                        )
                    }

                    composable(DestinationRoute.CAISSE_SORTIE_ROUTE){
                        SortieCaisseScreen(
                            onSaveClicked = {}
                        )
                    }

                    composable(DestinationRoute.CAISSE_TRANSFER_ROUTE){
                        TransferCaisseScreen (
                            onSaveClicked = {}
                        )
                    }
                    composable(DestinationRoute.CAISSE_SEE_ALL_ROUTE){
                        TransactionScreen()
                    }
                    composable(DestinationRoute.CAISSE_ACCOUNT_ROUTE){
                        AccountCaisseScreen()
                    }

                    composable(DestinationRoute.SETTING_ROUTE){
                        SettingScreen()
                    }
                }

               // }
            }
        }
    }
}




