package com.avenir.rangoapp.ui.screens.settings.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.data.models.PaymentMethodModel
import com.avenir.rangoapp.data.models.PaymentType
import com.avenir.rangoapp.data.models.listPaymentMethods


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Paiement") })
        }
    ) {
        LazyColumn(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 20.dp)
        ) {
            item {
                HorizontalDivider()

            }

            item {
                ListItem(headlineContent = {
                    Text(text = "Abonnement", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                },
                    supportingContent = {
                        Text(text = "Aucun abonnement actif", fontSize = 12.sp, color = Color.Gray)
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.usd_cercle_24),
                            contentDescription = ""
                        )
                    })
            }

            items(listPaymentMethods) { method ->
                LargeSpace()
                CardPayment(modifier, method = method)
            }

            item {
                LargeSpace()
                LargeSpace()
            }
        }
    }
}

@Composable
fun CardPayment(modifier: Modifier = Modifier, method: PaymentMethodModel) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (method.paymentType == PaymentType.PayAsYouGo)
                method.color
            else if (method.paymentType == PaymentType.Monthly)
                method.color
            else
                Color.Yellow
        ),
        modifier = Modifier.clip(
            shape = MaterialTheme.shapes.extraLarge
        )
    ) {
        Column(
            modifier = modifier
                .padding(20.dp)
        ) {
            Text(
                text = method.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            )
            SmallSpace()
            HorizontalDivider()
            SmallSpace()
            Text(text = " - 3 utilisateurs", fontSize = 12.sp)
            Text(text = " - Nombre illimité de produits", fontSize = 12.sp)
            Text(text = " - Nombre illimité de factures", fontSize = 12.sp)
            Text(" - Support 24/7 h", fontSize = 12.sp)
        }
    }
}