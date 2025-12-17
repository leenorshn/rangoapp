package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.data.models.ClientModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import com.vanpra.composematerialdialogs.message

@Composable
fun ClientSelectionDialog(
    clients: List<ClientModel>,
    onDismiss: () -> Unit,
    onClientSelected: (ClientModel) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    
    // Show dialog immediately when composable is called
    LaunchedEffect(Unit) {
        dialogState.show()
    }
    
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            negativeButton("Cancel") {
                onDismiss()
            }
        }
    ) {
        title(text = "Select Client")
        if (clients.isEmpty()) {
            message(text = "No clients available")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(clients) { client ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClientSelected(client)
                                dialogState.hide()
                                onDismiss()
                            }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = client.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = client.phone ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
