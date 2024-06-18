package com.avenir.rangoapp.ui.screens.store.provider

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.avenir.rangoapp.ui.components.ProviderItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderScreen(
    onNewProviderClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "My providers") }, actions = {
                Text(text = "4 providers")
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                onClick = { onNewProviderClicked() }) {
                Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "New provider")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                HorizontalDivider()
            }
            items(4) {

                ProviderItem()
            }
        }
    }
}