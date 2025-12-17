package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.data.models.ProductModel

@Composable
fun FactureProductItem(
    product: ProductModel,
    quantity: Int,
    onQuantityChanged: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    var quantityText by remember { mutableStateOf(quantity.toString()) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${product.priceVente} × $quantity = ${product.priceVente.toDouble() * quantity}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = quantityText,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                            quantityText = newValue
                            val qty = newValue.toIntOrNull() ?: 0
                            if (qty > 0 && qty <= product.stock.toInt()) {
                                onQuantityChanged(qty)
                            }
                        }
                    },
                    modifier = Modifier.width(60.dp),
                    singleLine = true
                )
                
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Remove",
                        tint = Color.Red
                    )
                }
            }
        }
        HorizontalDivider()
    }
}

