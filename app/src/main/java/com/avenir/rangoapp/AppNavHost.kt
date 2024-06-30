package com.avenir.rangoapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.avenir.rangoapp.core.DestinationRoute
import com.avenir.rangoapp.ui.loading.loadingScreenNavigation
import com.avenir.rangoapp.ui.screens.auth.login.loginNavigation
import com.avenir.rangoapp.ui.screens.auth.profile.profileNavGraph
import com.avenir.rangoapp.ui.screens.auth.register.RegisterScreen
import com.avenir.rangoapp.ui.screens.auth.register.account.RegisterViewModel
import com.avenir.rangoapp.ui.screens.auth.register.company.StepFinalScreen
import com.avenir.rangoapp.ui.screens.auth.register.account.StepOneScreen
import com.avenir.rangoapp.ui.screens.auth.register.company.StepThreeScreen
import com.avenir.rangoapp.ui.screens.auth.register.company.StepTwoScreen
import com.avenir.rangoapp.ui.screens.auth.welcome.welcomeNavigation
import com.avenir.rangoapp.ui.screens.caisse.account.accountCaisseNavigation
import com.avenir.rangoapp.ui.screens.caisse.caisseNavigation
import com.avenir.rangoapp.ui.screens.caisse.enter.enterCaisseNavigation
import com.avenir.rangoapp.ui.screens.caisse.sortie.sortieCaisseNavigation
import com.avenir.rangoapp.ui.screens.caisse.transaction.transactionNavigation
import com.avenir.rangoapp.ui.screens.caisse.transfer.transferNavigation
import com.avenir.rangoapp.ui.screens.facture.client.clientNavigation
import com.avenir.rangoapp.ui.screens.facture.client.newClient.newClientNavigation
import com.avenir.rangoapp.ui.screens.facture.facturation.factureNavigation
import com.avenir.rangoapp.ui.screens.facture.facturation.newfacture.newFactureNavigation
import com.avenir.rangoapp.ui.screens.home.homeNavigation
import com.avenir.rangoapp.ui.screens.settings.about.helpNavigation
import com.avenir.rangoapp.ui.screens.settings.cloud_storage.cloudStorageNavigation
import com.avenir.rangoapp.ui.screens.settings.payment.currencySettingsNavigation
import com.avenir.rangoapp.ui.screens.settings.payment.paymentSettingsNavigation
import com.avenir.rangoapp.ui.screens.settings.settingsNavigation
import com.avenir.rangoapp.ui.screens.settings.shop.shopSettingsNavigation
import com.avenir.rangoapp.ui.screens.settings.users.newuser.newUserNavigation
import com.avenir.rangoapp.ui.screens.settings.users.userSettingNavigation
import com.avenir.rangoapp.ui.screens.store.newproduct.newProductStoreNavigation
import com.avenir.rangoapp.ui.screens.store.provider.newprovider.newProviderNavigation
import com.avenir.rangoapp.ui.screens.store.provider.providerStoreNavigation
import com.avenir.rangoapp.ui.screens.store.rapport.rapportStoreNavigation
import com.avenir.rangoapp.ui.screens.store.storeNavigation


/**
 * Created by innov Victor on 3/14/2023.
 */

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    viewModel: MainViewModel= hiltViewModel()
) {
    val registerState by registerViewModel.state
    val state by viewModel.state.collectAsState()
    val startDestination = if (state.isLoggedIn) {
        DestinationRoute.MAIN_NAV_ROUTE
    } else if (state.isLoading) {
        DestinationRoute.LOADING_SCREEN_ROUTE
    } else {
        DestinationRoute.AUTH_ROUTE
    }
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        loadingScreenNavigation()

        navigation(
            startDestination = DestinationRoute.HOME_ROUTE,
            route = DestinationRoute.MAIN_NAV_ROUTE
        ) {
            //home
            homeNavigation(navController)
            caisseNavigation(navController)
            storeNavigation(navController)
            factureNavigation(navController)
            settingsNavigation(navController)

            //caisse
            accountCaisseNavigation(navController)
            enterCaisseNavigation(navController)
            sortieCaisseNavigation(navController)
            transferNavigation(navController)
            transactionNavigation(navController)

            //store
            rapportStoreNavigation()
            providerStoreNavigation(navController)
            newProviderNavigation(navController)
            newProductStoreNavigation(navController)

            //facture
            newFactureNavigation(navController)
            clientNavigation(navController)
            newClientNavigation(navController)

            //settings
            userSettingNavigation(navController)
            newUserNavigation(navController)
            shopSettingsNavigation(navController)
            currencySettingsNavigation(navController)
            paymentSettingsNavigation(navController)
            cloudStorageNavigation(navController)
            helpNavigation()

            //profile
            profileNavGraph(navController)


        }
        navigation(
            startDestination = DestinationRoute.WELCOME_ROUTE,
            route = DestinationRoute.AUTH_ROUTE,
        ) {
            welcomeNavigation(navController)
            loginNavigation(navController)


            composable(DestinationRoute.REGISTER_ROUTE) {
                RegisterScreen(
                    state= registerState,
                    onNext = {
                        navController.navigate(DestinationRoute.REGISTER_STEP_ONE_ROUTE)
                    },
                    onLogin = {
                        navController.navigate(DestinationRoute.LOGIN_ROUTE)
                    })

            }
            composable(DestinationRoute.REGISTER_STEP_ONE_ROUTE) {
                StepOneScreen(
                    state = registerState,
                    onNext = {
                        navController.navigate(DestinationRoute.REGISTER_STEP_TWO_ROUTE)
                    },
                    onEvent = registerViewModel::onTriggerEvent,
                    onPrevious = {
                        navController.popBackStack()
                    }
                )
            }

            composable(DestinationRoute.REGISTER_STEP_TWO_ROUTE) {
                StepTwoScreen(
                    state = registerState,
                    onNext = {
                        navController.navigate(DestinationRoute.REGISTER_STEP_THREE_ROUTE)
                    },
                    onEvent = registerViewModel::onTriggerEvent,
                    onPrevious = {
                        navController.popBackStack()
                    }
                )
            }
            composable(DestinationRoute.REGISTER_STEP_THREE_ROUTE) {
                StepThreeScreen(
                    state = registerState,
                    onNext = {
                        navController.navigate(DestinationRoute.REGISTER_STEP_FINAL_ROUTE)
                    },
                    onEvent = registerViewModel::onTriggerEvent,
                    onPrevious = {
                        navController.popBackStack()
                    }
                )
            }
            composable(DestinationRoute.REGISTER_STEP_FINAL_ROUTE) {
                StepFinalScreen(
                    state = registerState,

                    onEvent = registerViewModel::onTriggerEvent,
                onPrevious = {
                    navController.popBackStack()
                }
                )
            }




        }

    }

}

//pwd-aws=Waka@23$00_min