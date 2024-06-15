package com.avenir.rangoapp.ui.screens.store.product

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.ui.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    onNewProductClicked: () -> Unit,
    onProviderClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Products")
            },
                actions = {
                    ElevatedButton(onClick = { onProviderClicked() },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                        ) {
                      Icon(painter = painterResource(id = R.drawable.ic_friends),"",
                          modifier = Modifier.size(16.dp))
                      Spacer(modifier = Modifier.width(8.dp))
                      Text(text = "Providers")

                    }
                })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onNewProductClicked()
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus), contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "New Product")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(16.dp))
            }
            items(10) {
                ProductItem(index=it)
            }
            item { 
                Spacer(modifier = Modifier.size(120.dp))
            }
        }
    }
}