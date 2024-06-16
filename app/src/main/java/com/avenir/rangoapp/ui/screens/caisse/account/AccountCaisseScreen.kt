package com.avenir.rangoapp.ui.screens.caisse.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.theme.FailureColor
import com.avenir.rangoapp.ui.theme.SuccessColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCaisseScreen() {
    val comptes by remember {
        mutableStateOf(listAccount)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Mes comptes")
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item {
                Text(text = "")
            }
            items(comptes) { account ->
                OutlinedCard(onClick = { /*TODO*/ }, modifier = Modifier.padding(top=8.dp)) {
                    ListItem(headlineContent = {
                        Text(text = account.name, fontSize = 24.sp)
                    },
                        overlineContent = {
                            Text(text = "Compte")
                        }, supportingContent = {
                            Row {
                                account.currencies.map {
                                    Text(text = "$it ")
                                }
                            }

                        }, trailingContent = {
                            Text(
                                text = "(${account.type})",

                                color = if (account.type == "business") Color.Cyan else if (account.type == "principal") SuccessColor else FailureColor
                            )
                        })
                }
            }
            item {
                48.dp.Space()
                Box (
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ){
                    ElevatedButton(
                        modifier = Modifier.height(64.dp),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.elevatedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary,
                        ),
                        onClick = {}) {
                        Text(text = "Ajouter compte")
                    }
                }
            }
        }
    }
}