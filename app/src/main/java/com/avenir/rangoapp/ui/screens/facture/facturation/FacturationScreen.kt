package com.avenir.rangoapp.ui.screens.facture.facturation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.SaleCardItem


@Composable
fun FacturationScreen(
    state: FactureState,
    onEvent: (FactureEvent) -> Unit,
    onClientClicked:()->Unit,
    onNewFactureClicked:()->Unit,
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                contentColor = MaterialTheme.colorScheme.onTertiary,
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClick = { onNewFactureClicked() }) {
                Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Nouvelle Vente")
            }
        }
    ) {
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(it),
                color = Color.Yellow,
            )
        } else if (state.error != null) {
            Text(
                text = state.error,
                modifier = Modifier.fillMaxWidth().padding(it),
                color = Color.Red
            )
        }
        
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Ventes",
                        fontSize = 24.sp,
                    )
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        onClick = { onClientClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_friends),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Clients")
                    }
                }
                SmallSpace()
                HorizontalDivider()
            }
            item {
                SmallSpace()
            }
            if (state.sales.isEmpty() && !state.isLoading && state.error == null) {
                item {
                    Text(
                        text = "Aucune vente trouvée",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color.Gray
                    )
                }
            } else {
                items(state.sales) { sale ->
                    SaleCardItem(sale = sale)
                }
            }
        }
    }
}