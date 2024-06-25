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
import androidx.navigation.navigation



/**
 * Created by innov Victor on 3/14/2023.
 */

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
   // startDestination: String = DestinationRoute.AUTH_ROUTE,
    mainViewModel: MainViewModel= hiltViewModel()
) {
    val authState by mainViewModel.uiState.collectAsState()
   // val context = LocalContext.current



    val initialRoute = if(authState.loading) {
        "loading"
   } else if (authState.error!=null){
        DestinationRoute.AUTH_ROUTE
    } else if (authState.success){
        DestinationRoute.MAIN_NAV_ROUTE
    } else  {
        DestinationRoute.AUTH_ROUTE
    }
    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = modifier
    ) {
        //mainViewModel.onTriggerEvent(MainEvent.OnReloadUser(context))
        navigation(
            startDestination = DestinationRoute.HOME_SCREEN_ROUTE,
            route = DestinationRoute.MAIN_NAV_ROUTE
        ) {
            homeNavGraph(navController)
            cameraMediaNavGraph(navController)
            creatorProfileNavGraph(navController)
            watchCompetitionNavGraph(navController)
            marketNavGraph(navController)
            joinCompetitionNavGraph(navController)
            uploadNavGraph(navController)
            voteCompetitionNavGraph(navController)
            videoNavGraph(navController)
            publicationNavGraph(navController)
            myProfileNavGraph(navController)
            firstProfileNavGraph(navController)
            commentListingNavGraph(navController)
            rechargeNavGraph(navController)
            notificationNavGraph(navController)
            videoDetailNavGraph(navController)
            myVideosNavGraph(navController)
            myBusinessNavGraph(navController)
            monetisationNavGraph(navController)

        }
        navigation(DestinationRoute.AUTHENTICATION_ROUTE, DestinationRoute.AUTH_ROUTE) {
            //welcome,phone,code,Profile
            authNavGraph(navController)
            phoneNavGraph(navController)
            optNavGraph(navController)
            profileSettingNavGraph(navController)
        }

        navigation(startDestination = DestinationRoute.LOADING_SCREEN, "loading") {
            composable(route = DestinationRoute.LOADING_SCREEN) {
                LoadingScreen(message = "loading")
            }
        }

    }
}

//pwd-aws=Waka@23$00_min