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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.HomeMenuItem
import com.avenir.rangoapp.ui.theme.GrayColor
import com.avenir.rangoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState?,
    onProfileClicked: () -> Unit,
    onFactureClicked: () -> Unit,
    onStoreClicked: () -> Unit,
    onCaisseClicked:()->Unit,
    onSettingClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("RangoApp") },

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
            WelcomeBox(name = if(state?.user?.name.isNullOrEmpty()) {"Guest"} else{ "${state?.user?.name}"})
            32.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Activity")
                Icon(Icons.Outlined.List, contentDescription = "All menus")
            }
            24.dp.Space()
            HomeMenuItem(icon = painterResource(id = R.drawable.ic_plus), title = "Facture", onTapMenu = {
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
fun WelcomeBox(modifier: Modifier = Modifier, name: String) {
    Column {
        16.dp.Space()
        Row {
            Text("Welcome , ", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = "  $name !",
                color = Color.Cyan,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )

        }
        8.dp.Space()
        Text(
            text = "Manage your business easily and have all control for your success !",
            color = GrayColor,
        )
    }
}