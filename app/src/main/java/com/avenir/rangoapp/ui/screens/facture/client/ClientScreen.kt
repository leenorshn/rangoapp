package com.avenir.rangoapp.ui.screens.facture.client

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.ui.components.ClientItem
import com.avenir.rangoapp.ui.theme.GrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    onNewClient: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "My clients") }, actions = {
                Text(text = "4 clients")
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                onClick = { onNewClient() }) {
                Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "New client")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                HorizontalDivider()
            }
            items(4) {

                ClientItem()
            }
        }
    }
}