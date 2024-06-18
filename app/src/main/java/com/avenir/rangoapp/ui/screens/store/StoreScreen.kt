package com.avenir.rangoapp.ui.screens.store

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.ui.components.ProductItem


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    onAddNewProductClicked: () -> Unit,
    onRapportStoreClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Store")
            }, actions = {
                ElevatedButton(onClick = { onRapportStoreClicked() },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                    )
                    ) {
                    Icon(painter = painterResource(id = R.drawable.carnet_24),"")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Rapport")
                }
            })
        },
        //floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                ExtendedFloatingActionButton(
                    onClick = {
                              onAddNewProductClicked()
                    },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.height(64.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add product")
                }

            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                HorizontalDivider()
            }
            items(listOfProducts) { product ->
                ProductItem(product=product)

            }
        }
    }
}