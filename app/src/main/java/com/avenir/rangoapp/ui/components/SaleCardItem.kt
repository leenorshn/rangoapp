package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.data.models.SaleModel

@Composable
fun SaleCardItem(
    sale: SaleModel,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .padding(top = 1.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = RoundedCornerShape(10),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary,
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = sale.date, fontSize = 14.sp)
                Text(text = sale.client.name, fontSize = 18.sp)
                Text(
                    text = "${sale.currency} ${sale.priceToPay}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                if (sale.change > 0) {
                    Text(
                        text = "Monnaie: ${sale.currency} ${sale.change}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
            Icon(painter = painterResource(id = R.drawable.impression_24), contentDescription = "Print")
        }
        HorizontalDivider()
    }
}


