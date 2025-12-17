package com.avenir.rangoapp.ui.screens.caisse.sortie


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.screens.caisse.sortie.SortieCaisseViewModel
import com.avenir.rangoapp.ui.screens.caisse.sortie.SortieCaisseEvent
import com.avenir.rangoapp.ui.theme.GrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortieCaisseScreen(
    viewModel: SortieCaisseViewModel,
    onSaveClicked: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var currency by remember {
        mutableStateOf(state.currency)
    }
    var amountText by remember {
        mutableStateOf(TextFieldValue(text = ""))
    }
    var descriptionText by remember {
        mutableStateOf(TextFieldValue(text = state.description))
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Sortie en caisse") })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            40.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Monnaie")
                Row {
                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .height(40.dp)
                            .background(
                                color = if (currency == "CDF") Color.Yellow else Color.Transparent,
                                RoundedCornerShape(20)
                            )
                            .border(
                                1.dp,
                                color = if (currency == "CDF") Color.Transparent else Color.Gray,
                                RoundedCornerShape(20)
                            )
                            .clickable {
                                currency = "CDF"
                                viewModel.onTriggerEvent(SortieCaisseEvent.OnCurrencyChanged("CDF"))
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CDF",
                            color = if (currency == "CDF") Color.Black else Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .height(40.dp)
                            .background(
                                color = if (currency == "USD") Color.Yellow else Color.Transparent,
                                RoundedCornerShape(20)
                            )
                            .border(
                                1.dp,
                                color = if (currency == "USD") Color.Transparent else Color.Gray,
                                RoundedCornerShape(20)
                            )
                            .clickable {
                                currency = "USD"
                                viewModel.onTriggerEvent(SortieCaisseEvent.OnCurrencyChanged("USD"))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "USD",
                            color = if (currency == "USD") Color.Black else Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
            40.dp.Space()
            OutlinedButton(
                shape = RoundedCornerShape(16),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary,
                ),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.verified_icon),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(text = "Selectionner le compte benefiaire", fontSize = 22.sp)
            }
            40.dp.Space()
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = GrayColor,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(
                        RoundedCornerShape(16)
                    ),
                value = amountText,
                onValueChange = { value ->
                    amountText = value
                    val amount = value.text.toDoubleOrNull() ?: 0.0
                    viewModel.onTriggerEvent(SortieCaisseEvent.OnAmountChanged(amount))
                },
                label = {
                    Text("Montant")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dollar_24),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                },

                )
            20.dp.Space()
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = GrayColor,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(
                        RoundedCornerShape(16)
                    ),
                value = descriptionText,
                onValueChange = { value ->
                    descriptionText = value
                    viewModel.onTriggerEvent(SortieCaisseEvent.OnDescriptionChanged(value.text))
                },
                label = {
                    Text("Libele")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Create, contentDescription = "")
                },

                )

            Spacer(modifier = Modifier.weight(1f))
            ElevatedButton(
                shape = RoundedCornerShape(16),
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                onClick = {
                    viewModel.onTriggerEvent(SortieCaisseEvent.OnSubmit)
                    if (state.success) {
                        onSaveClicked()
                    }
                },
                enabled = !state.isLoading && state.amount > 0
            ) {
                Text(
                    text = "Enregistrer",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 14.dp)
                )
            }
            40.dp.Space()
        }
    }
}