package com.avenir.rangoapp.ui.screens.store

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.ui.components.ProductInStoreWidget


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    onAddNewProductClicked:()->Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Store")
            }, actions = {
                Text(" 10 items", color = MaterialTheme.colorScheme.tertiary)
            })
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                ExtendedFloatingActionButton(
                    onClick = {
                              onAddNewProductClicked()
                    },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.width(260.dp)
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
            items(5) {
                ProductInStoreWidget()
            }
        }
    }
}