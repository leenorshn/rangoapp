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
import com.avenir.rangoapp.ui.screens.auth.register.store.StoreState
import com.avenir.rangoapp.ui.screens.auth.register.store.StoreEvent
import com.avenir.rangoapp.ui.screens.auth.register.summary.SummaryScreen
import com.avenir.rangoapp.ui.screens.auth.register.summary.PersonalInfo
import com.avenir.rangoapp.ui.screens.auth.register.summary.CompanyInfo
import com.avenir.rangoapp.ui.screens.auth.register.summary.StoreInfo
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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

    // Toujours afficher le login en premier
    val startDestination = DestinationRoute.AUTH_ROUTE
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
            startDestination = DestinationRoute.LOGIN_ROUTE,
            route = DestinationRoute.AUTH_ROUTE,
        ) {
            welcomeNavigation(navController)
            loginNavigation(navController)

            composable(DestinationRoute.REGISTER_STEP_ONE_ROUTE) {
                StepOneScreen(
                    state = registerState,
                    onEvent = registerViewModel::onTriggerEvent,
                    navigateToCompanyCreation = {
                        // Pass user data to CompanyViewModel before navigation
                        companyViewModel.setUserData(
                            password = registerState.password,
                            name = registerState.username.split("@").firstOrNull() ?: registerState.username,
                            phone = "" // Will use company phone if not provided
                        )
                        navController.navigate(DestinationRoute.REGISTER_STEP_TWO_ROUTE)
                    }
                )
            }

            // Étape 2: Informations de l'entreprise
            composable(DestinationRoute.REGISTER_STEP_TWO_ROUTE) {
                StepTwoScreen(
                    state = companyState,
                    onNext = {
                        navController.navigate(DestinationRoute.REGISTER_STEP_THREE_ROUTE)
                    },
                    onEvent = companyViewModel::onTriggerEvent,
                )
            }

            // Étape 3: Informations du magasin
            composable(DestinationRoute.REGISTER_STEP_THREE_ROUTE) {
                val storeState = remember {
                    mutableStateOf(
                        StoreState(
                            name = companyState.storeName,
                            address = companyState.storeAddress,
                            phone = companyState.storePhone
                        )
                    )
                }
                
                StepThreeScreen(
                    state = storeState.value,
                    onEvent = { event ->
                        when (event) {
                            is StoreEvent.NameChanged -> {
                                storeState.value = storeState.value.copy(name = event.name)
                                companyViewModel.onTriggerEvent(
                                    com.avenir.rangoapp.ui.screens.auth.register.company.CompanyEvent.StoreNameChanged(event.name)
                                )
                            }
                            is StoreEvent.AddressChanged -> {
                                storeState.value = storeState.value.copy(address = event.address)
                                companyViewModel.onTriggerEvent(
                                    com.avenir.rangoapp.ui.screens.auth.register.company.CompanyEvent.StoreAddressChanged(event.address)
                                )
                            }
                            is StoreEvent.PhoneChanged -> {
                                storeState.value = storeState.value.copy(phone = event.phone)
                                companyViewModel.onTriggerEvent(
                                    com.avenir.rangoapp.ui.screens.auth.register.company.CompanyEvent.StorePhoneChanged(event.phone)
                                )
                            }
                        }
                    },
                    onNext = {
                        companyViewModel.setStoreData(
                            name = storeState.value.name,
                            address = storeState.value.address,
                            phone = storeState.value.phone
                        )
                        navController.navigate(DestinationRoute.REGISTER_STEP_SUMMARY_ROUTE)
                    },
                    onPrevious = {
                        navController.popBackStack()
                    }
                )
            }

            // Étape 4: Résumé
            composable(DestinationRoute.REGISTER_STEP_SUMMARY_ROUTE) {
                SummaryScreen(
                    personalInfo = PersonalInfo(
                        name = companyState.userName.ifEmpty { 
                            registerState.username.split("@").firstOrNull() ?: registerState.username 
                        },
                        phone = companyState.userPhone.ifEmpty { companyState.phone }
                    ),
                    companyInfo = CompanyInfo(
                        name = companyState.name,
                        address = companyState.address,
                        phone = companyState.phone
                    ),
                    storeInfo = StoreInfo(
                        name = companyState.storeName,
                        address = companyState.storeAddress,
                        phone = companyState.storePhone
                    ),
                    isLoading = companyState.isLoading,
                    error = companyState.error,
                    onPrevious = {
                        navController.popBackStack()
                    },
                    onSubmit = {
                        companyViewModel.onTriggerEvent(
                            com.avenir.rangoapp.ui.screens.auth.register.company.CompanyEvent.OnSubmit
                        )
                    }
                )
                
                // Navigate to home when registration is successful
                LaunchedEffect(companyState.isSuccess) {
                    if (companyState.isSuccess) {
                        navController.navigate(DestinationRoute.MAIN_NAV_ROUTE) {
                            popUpTo(DestinationRoute.AUTH_ROUTE) { inclusive = true }
                        }
                    }
                }
            }




        }

    }

}

//pwd-aws=Waka@23$00_min