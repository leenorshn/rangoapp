package com.avenir.rangoapp.ui.screens.facture.facturation.newfacture

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.data.repository.ClientRepository
import com.avenir.rangoapp.ui.components.ClientSelectionDialog
import com.avenir.rangoapp.ui.components.DatePickerWidgetWithCallback
import com.avenir.rangoapp.ui.components.FactureProductItem
import com.avenir.rangoapp.ui.components.ProductSelectionDialog
import com.avenir.rangoapp.ui.theme.GrayColor
import com.avenir.rangoapp.ui.theme.PrimaryColor
import com.avenir.rangoapp.ui.theme.SecondaryColor
import com.avenir.rangoapp.ui.theme.SuccessColor
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import com.avenir.rangoapp.ui.screens.facture.facturation.newfacture.toRFC3339
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFactureScreen(
    state: NewFactureState,
    onEvent: (NewFactureEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    var showClientDialog by remember { mutableStateOf(false) }
    var showProductDialog by remember { mutableStateOf(false) }
    
    val selectedDate = remember {
        try {
            // Try to parse RFC3339 format first
            try {
                java.time.Instant.parse(state.date).atZone(ZoneOffset.UTC).toLocalDate()
            } catch (e: Exception) {
                // Fallback to ISO_LOCAL_DATE for backward compatibility
                LocalDate.parse(state.date, DateTimeFormatter.ISO_LOCAL_DATE)
            }
        } catch (e: Exception) {
            LocalDate.now()
        }
    }

    LaunchedEffect(key1 = state.success) {
        if (state.success) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "New Invoice") })
        },
        bottomBar = {
            Column(modifier = Modifier.padding(bottom = 32.dp, start = 24.dp, end = 24.dp)) {
                if (state.error != null) {
                    Text(
                        text = state.error,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Text(text = "Total:", color = Color.Gray)
                Text(
                    text = String.format("%.2f %s", state.total, state.currency),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W300
                )
                16.dp.Space()
                Row {
                    OutlinedButton(
                        onClick = { onEvent(NewFactureEvent.OnSaveDraft) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        modifier = Modifier.weight(1f),
                        enabled = !state.isLoading && state.selectedClient != null && state.selectedProducts.isNotEmpty()
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Text(text = "Draft", modifier = Modifier.padding(vertical = 14.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        onClick = { onEvent(NewFactureEvent.OnSaveInvoice) },
                        modifier = Modifier.weight(1f),
                        enabled = !state.isLoading && state.selectedClient != null && state.selectedProducts.isNotEmpty()
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        } else {
                            Text(text = "Save Invoice", modifier = Modifier.padding(vertical = 14.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "No:")
                Text(
                    text = "F001/2024",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            16.dp.Space()
            Divider()
            24.dp.Space()

            DatePickerWidgetWithCallback(
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    onEvent(NewFactureEvent.OnDateChanged(date.toRFC3339()))
                }
            )

            24.dp.Space()
            Divider()
            16.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "Currency :")
                Row {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (state.currency == "USD") SuccessColor else Color.White,
                                shape = RoundedCornerShape(20)
                            )
                            .border(1.dp, color = GrayColor, RoundedCornerShape(20))
                            .padding(10.dp)
                            .clickable {
                                onEvent(NewFactureEvent.OnCurrencyChanged("USD"))
                            }
                    ) {
                        Text(
                            text = "USD",
                            color = if (state.currency == "USD") PrimaryColor else SecondaryColor,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (state.currency == "CDF") SuccessColor else Color.White,
                                shape = RoundedCornerShape(20)
                            )
                            .padding(10.dp)
                            .clickable {
                                onEvent(NewFactureEvent.OnCurrencyChanged("CDF"))
                            }
                    ) {
                        Text(
                            text = "CDF",
                            color = if (state.currency == "CDF") PrimaryColor else SecondaryColor,
                        )
                    }
                }
            }
            16.dp.Space()
            Divider()
            20.dp.Space()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Customer")
                ElevatedButton(
                    onClick = { showClientDialog = true },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                    )
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = state.selectedClient?.name ?: "Select client"
                    )
                }
            }
            20.dp.Space()
            Divider()
            20.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(text = "Taxes TVA")
                    Text(text = "16%", color = Color.Gray)
                }
                Checkbox(
                    checked = state.isTvaEnabled,
                    onCheckedChange = { onEvent(NewFactureEvent.OnTvaEnabledChanged(it)) },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = SuccessColor,
                    ),
                    modifier = Modifier.border(1.dp, color = GrayColor, RoundedCornerShape(4.dp))
                )
            }
            16.dp.Space()
            Divider()
            16.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "Product")
                Text(text = "Quantity")
            }
            16.dp.Space()
            
            if (state.selectedProducts.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(100.dp)
                            .background(
                                color = Color.Gray.copy(alpha = 1f),
                                shape = RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(20))
                            .clickable { showProductDialog = true },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_plus),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(text = "Add product")
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(state.selectedProducts) { (product, quantity) ->
                        FactureProductItem(
                            product = product,
                            quantity = quantity,
                            onQuantityChanged = { newQuantity ->
                                onEvent(NewFactureEvent.OnProductQuantityChanged(product.id, newQuantity))
                            },
                            onRemove = {
                                onEvent(NewFactureEvent.OnProductRemoved(product.id))
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(
                                    color = Color.Gray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(10)
                                )
                                .clip(RoundedCornerShape(10))
                                .clickable { showProductDialog = true },
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_plus),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Add product")
                            }
                        }
                    }
                }
            }
        }
    }

    // Client Selection Dialog
    if (showClientDialog) {
        ClientSelectionDialog(
            clients = state.availableClients,
            onDismiss = { showClientDialog = false },
            onClientSelected = { client ->
                onEvent(NewFactureEvent.OnClientSelected(client))
            }
        )
    }

    // Product Selection Dialog
    if (showProductDialog) {
        ProductSelectionDialog(
            products = state.availableProducts,
            onDismiss = { showProductDialog = false },
            onProductSelected = { product, quantity ->
                onEvent(NewFactureEvent.OnProductAdded(product, quantity))
            }
        )
    }
}
