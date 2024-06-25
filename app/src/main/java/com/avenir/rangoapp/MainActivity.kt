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
import com.avenir.rangoapp.ui.loading.LoadingScreen
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































                }

                // }
            }
        }
    }
}




