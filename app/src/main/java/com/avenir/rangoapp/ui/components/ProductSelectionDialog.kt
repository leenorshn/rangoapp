package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.data.models.ProductModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import com.vanpra.composematerialdialogs.message

@Composable
fun ProductSelectionDialog(
    products: List<ProductModel>,
    onDismiss: () -> Unit,
    onProductSelected: (ProductModel, Int) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var selectedProduct: ProductModel? by remember { mutableStateOf(null) }
    var quantity by remember { mutableStateOf("1") }

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
            positiveButton("Add") {
                selectedProduct?.let { product ->
                    val qty = quantity.toIntOrNull() ?: 1
                    if (qty > 0 && qty <= product.stock.toInt()) {
                        onProductSelected(product, qty)
                        dialogState.hide()
                        onDismiss()
                    }
                }
            }
        }
    ) {
        title(text = "Select Product")
        
        if (products.isEmpty()) {
            message(text = "No products available")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(products) { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedProduct = product
                                    quantity = "1"
                                }
                                .padding(vertical = 12.dp, horizontal = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = product.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "${product.priceVente}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "Stock: ${product.stock}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (product.stock.toInt() > 0) Color.Green else Color.Red
                                    )
                                }
                                if (selectedProduct?.id == product.id) {
                                    Text(
                                        text = "✓",
                                        color = Color.Green,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
                
                if (selectedProduct != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Quantity",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        TextField(
                            value = quantity,
                            onValueChange = { 
                                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                                    quantity = it
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("Enter quantity") }
                        )
                        if (quantity.toIntOrNull()?.let { 
                            it > (selectedProduct?.stock?.toInt() ?: 0) 
                        } == true) {
                            Text(
                                text = "Stock available: ${selectedProduct?.stock}",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
