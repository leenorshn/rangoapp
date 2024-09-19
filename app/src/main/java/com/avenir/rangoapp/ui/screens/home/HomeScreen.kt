package com.avenir.rangoapp.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.HomeMenuItem
import com.avenir.rangoapp.ui.theme.GrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState?,
    event: (e:HomeEvent)->Unit,
    onProfileClicked: () -> Unit,
    onFactureClicked: () -> Unit,
    onStoreClicked: () -> Unit,
    onCaisseClicked:()->Unit,
    onSettingClicked: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        event.invoke(HomeEvent.OnLoadVideo)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Dooka") },

                actions = {
                    Box(
                        modifier = Modifier.border(
                            width = 0.7.dp,
                            color = GrayColor,
                            shape = RoundedCornerShape(50)
                        )

                    ) {
                        IconButton(onClick = {
                            onProfileClicked()
                        }) {
                            Icon(
                                Icons.Filled.Person, contentDescription = "Icon profile",
                                modifier = Modifier.size(24.dp),
                            )
                        }

                    }
                    Spacer(modifier = Modifier.padding(end = 10.dp))

                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            if (state?.isLoading==true){
                LinearProgressIndicator(
                    color = Color.Yellow,
                    modifier = Modifier.fillMaxWidth()
                )
            }else if (state?.error!=null){
                Text(text = "Error de chargement", color = Color.Red)
            }
                //state?.user?.name?.let {
                    WelcomeBox(
                       homeState = state
                    )



            32.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Activity")
                Icon(Icons.AutoMirrored.Outlined.List, contentDescription = "All menus")
            }
            24.dp.Space()
            HomeMenuItem(icon = painterResource(id = R.drawable.list), title = "Facture", onTapMenu = {
                onFactureClicked()
            })
            //24.dp.Space()
//            HomeMenuItem(icon=painterResource(id = R.drawable.ic_friends), title = "Clients", onTapMenu = {
//                onClientClicked()
//            })
            24.dp.Space()
            HomeMenuItem(icon =painterResource(id = R.drawable.ic_home_fill), title = "Store", onTapMenu = {
                onStoreClicked()
            })
            24.dp.Space()
            HomeMenuItem(icon=painterResource(id = R.drawable.dollar_24), title = "Caisse", onTapMenu = {
                onCaisseClicked()
            })
            24.dp.Space()
            HomeMenuItem(icon = painterResource(id = R.drawable.reglages_24), title = "Settings", onTapMenu = {
                onSettingClicked()
            })

            LargeSpace()
            LargeSpace()
            Text(
                text = "Created by: Avenir-C0",
                fontSize = 12.sp,
                color = Color.Cyan,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

        }
    }
}


@Composable
fun WelcomeBox(modifier: Modifier = Modifier, homeState: HomeState?) {
    Column {
        16.dp.Space()
        Row {
            Text("Bienvenu , ", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            homeState?.let {
                Text(
                    text = " ${if (it.user?.name.isNullOrEmpty()) "" else it.user?.name +"!"} ",
                    color = Color.Yellow,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
        8.dp.Space()
        Text(
            text = "Gerer facilement votre commerce !",
            color = GrayColor,
        )
    }
}