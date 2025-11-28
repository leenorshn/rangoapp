package com.avenir.rangoapp.ui.screens.facture.client

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.ui.components.ClientItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen(
    state: ClientState,
    onNewClient: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My clients") },
                actions = {
                    Text(text = "${state.clients.size} clients")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                onClick = { onNewClient() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "New client")
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(paddingValues),
                color = Color.Yellow
            )
        } else if (state.error != null) {
            Text(
                text = state.error,
                modifier = Modifier.fillMaxWidth().padding(paddingValues),
                color = Color.Red
            )
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                item {
                    HorizontalDivider()
                }
                if (state.clients.isEmpty() && !state.isLoading && state.error == null) {
                    item {
                        Text(
                            text = "Aucun client trouvé",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = Color.Gray
                        )
                    }
                } else {
                    items(state.clients) { client ->
                        ClientItem(client = client)
                    }
                }
            }
        }
    }
}