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

import com.avenir.rangoapp.ui.screens.auth.register.account.RegisterViewModel
import com.avenir.rangoapp.ui.screens.auth.register.account.StepOneScreen
import com.avenir.rangoapp.ui.screens.auth.register.company.CompanyViewModel
import com.avenir.rangoapp.ui.screens.auth.register.company.StepTwoScreen
import com.avenir.rangoapp.ui.screens.auth.register.store.StepThreeScreen
import androidx.compose.runtime.LaunchedEffect

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
import com.avenir.rangoapp.ui.screens.settings.payment.currencySettingsNavigation
import com.avenir.rangoapp.ui.screens.settings.payment.paymentSettingsNavigation
import com.avenir.rangoapp.ui.screens.settings.settingsNavigation
import com.avenir.rangoapp.ui.screens.settings.shop.shopSettingsNavigation
import com.avenir.rangoapp.ui.screens.settings.users.newuser.newUserNavigation
import com.avenir.rangoapp.ui.screens.settings.users.userSettingNavigation
import com.avenir.rangoapp.ui.screens.stock.newproduct.newProductStoreNavigation
import com.avenir.rangoapp.ui.screens.stock.provider.newprovider.newProviderNavigation
import com.avenir.rangoapp.ui.screens.stock.provider.providerStoreNavigation
import com.avenir.rangoapp.ui.screens.stock.rapport.rapportStoreNavigation
import com.avenir.rangoapp.ui.screens.stock.storeNavigation


/**
 * Created by innov Victor on 3/14/2023.
 */

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    companyViewModel: CompanyViewModel = hiltViewModel(),
    viewModel: MainViewModel= hiltViewModel()
) {
    val registerState by registerViewModel.state
    val state by viewModel.state.collectAsState()

    val companyState by companyViewModel.state

    // Déterminer la destination de départ selon l'état d'authentification
    val startDestination = if (state.isLoggedIn && !state.isLoading) {
        DestinationRoute.MAIN_NAV_ROUTE
    } else {
        DestinationRoute.AUTH_ROUTE
    }

    // Gérer la navigation automatique selon l'état d'authentification
    LaunchedEffect(key1 = state.isLoggedIn, key2 = state.isLoading) {
        if (!state.isLoading) {
            if (state.isLoggedIn) {
                // Si l'utilisateur est connecté, naviguer vers la page principale
                navController.navigate(DestinationRoute.MAIN_NAV_ROUTE) {
                    // Supprimer toutes les routes d'authentification de la pile
                    popUpTo(DestinationRoute.AUTH_ROUTE) { inclusive = true }
                    // Éviter les multiples navigations
                    launchSingleTop = true
                }
            } else {
                // Si l'utilisateur n'est pas connecté, naviguer vers la page d'accueil
                navController.navigate(DestinationRoute.AUTH_ROUTE) {
                    // Supprimer toutes les routes principales de la pile
                    popUpTo(DestinationRoute.MAIN_NAV_ROUTE) { inclusive = true }
                    // Éviter les multiples navigations
                    launchSingleTop = true
                }
            }
        }
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

            composable(DestinationRoute.REGISTER_STEP_ONE_ROUTE) {
                StepOneScreen(
                    state = registerState,
                    onEvent = registerViewModel::onTriggerEvent,
                    navigateToCompanyCreation = {
                        navController.navigate(DestinationRoute.REGISTER_STEP_TWO_ROUTE)
                    }
                )
            }

            // Étape 2: Informations de l'entreprise
            composable(DestinationRoute.REGISTER_STEP_TWO_ROUTE) {
                LaunchedEffect(key1 = companyState.isSuccess) {
                    if (companyState.isSuccess) {
                        navController.navigate(DestinationRoute.REGISTER_STEP_THREE_ROUTE)
                    }
                }
                StepTwoScreen(
                    state = companyState,
                    onNext = {
                        // Navigation handled by LaunchedEffect when isSuccess is true
                    },
                    onEvent = companyViewModel::onTriggerEvent,
                )
            }
            
            // Étape 3: Liste des stores (si vide, afficher "Pas de Boutique")
            composable(DestinationRoute.REGISTER_STEP_THREE_ROUTE) {
                val storeViewModel: com.avenir.rangoapp.ui.screens.auth.register.store.StoreViewModel = hiltViewModel()
                val storeState by storeViewModel.state
                
                LaunchedEffect(key1 = storeState.isSuccess) {
                    if (storeState.isSuccess) {
                        // Navigate to home or login after successful store creation
                        navController.navigate(DestinationRoute.LOGIN_ROUTE) {
                            popUpTo(DestinationRoute.AUTH_ROUTE) { inclusive = true }
                        }
                    }
                }
                
                com.avenir.rangoapp.ui.screens.auth.register.store.StoreListScreen(
                    stores = emptyList(), // Empty list means no stores yet
                    onAddStore = {
                        navController.navigate("${DestinationRoute.REGISTER_STEP_THREE_ROUTE}/create")
                    }
                )
            }
            
            // Étape 4: Création de la première boutique
            composable("${DestinationRoute.REGISTER_STEP_THREE_ROUTE}/create") {
                val storeViewModel: com.avenir.rangoapp.ui.screens.auth.register.store.StoreViewModel = hiltViewModel()
                val storeState by storeViewModel.state
                
                LaunchedEffect(key1 = storeState.isSuccess) {
                    if (storeState.isSuccess) {
                        // Navigate to home or login after successful store creation
                        navController.navigate(DestinationRoute.LOGIN_ROUTE) {
                            popUpTo(DestinationRoute.AUTH_ROUTE) { inclusive = true }
                        }
                    }
                }
                
                StepThreeScreen(
                    state = storeState,
                    onEvent = storeViewModel::onTriggerEvent,
                    onNext = {
                        // Navigation handled by LaunchedEffect
                    },
                    onPrevious = {
                        navController.popBackStack()
                    }
                )
            }


            // Note: Summary screen removed - each step is now independent and complete
            // Step 1: Register user account
            // Step 2: Create company
            // Step 3: List stores (empty initially)
            // Step 4: Create first store




        }

    }

}

//pwd-aws=Waka@23$00_min