package com.avenir.rangoapp.ui.screens.caisse.transaction

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.theme.CardColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen() {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "Transactions")
                })
            }
        ) {
            LazyColumn(modifier = Modifier.padding(it).fillMaxSize()) {
                items(8){
                    Card(onClick = { /*TODO*/ },
                        colors = CardDefaults.cardColors(
                            containerColor = CardColor,
                        )) {
                        ListItem(
                            supportingContent = {
                                Text(text = "Entrée")
                            },
                            headlineContent = {
                                Text(text = "Operation")
                            }, leadingContent = {
                                Text(text = "$ 1000")
                            }, trailingContent = {
                                Icon(Icons.AutoMirrored.Outlined.ArrowForward,"")
                            })
                        HorizontalDivider()
                    }
                }

                item {
                    120.dp.Space()
                }
            }
        }
}