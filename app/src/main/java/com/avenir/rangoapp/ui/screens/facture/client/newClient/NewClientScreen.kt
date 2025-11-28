package com.avenir.rangoapp.ui.screens.facture.client.newClient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.TextInputWidget
import com.avenir.rangoapp.ui.theme.GrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewClientScreen(
    state: NewClientState,
    onEvent: (NewClientEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(key1 = state.success) {
        if (state.success) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "New client") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            40.dp.Space()
            
            if (state.error != null) {
                Text(
                    text = state.error,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )
            }
            
            TextInputWidget(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = { onEvent(NewClientEvent.OnNameChanged(it)) },
                label = "Client name",
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                }
            )
            
            20.dp.Space()
            
            TextInputWidget(
                modifier = Modifier.fillMaxWidth(),
                value = state.phone,
                onValueChange = { onEvent(NewClientEvent.OnPhoneChanged(it)) },
                label = "Client phone",
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Phone, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    color = Color.Yellow
                )
            } else {
                ElevatedButton(
                    shape = RoundedCornerShape(16),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    onClick = { onEvent(NewClientEvent.OnSubmit) }
                ) {
                    Text(
                        text = "Save client",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 14.dp)
                    )
                }
            }
            40.dp.Space()
        }
    }
}