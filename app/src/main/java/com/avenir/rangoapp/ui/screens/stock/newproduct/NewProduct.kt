package com.avenir.rangoapp.ui.screens.stock.newproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.TextInputWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductScreen(
    state: NewProductState,
    onEvent: (event:NewProductEvent) -> Unit,
    navigateToProducts:()->Unit
    ) {
    LaunchedEffect(key1 = state.success) {
        if (state.success==true) {
            navigateToProducts()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Nouveau produit")
            },
                actions = {
                    TextButton(
                        onClick = {
                            onEvent(NewProductEvent.OnSubmit)
                        },

                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.onTertiary,
                            contentColor = MaterialTheme.colorScheme.tertiary,
                        ),
                    ) {
                        Text(text = "Enregistrer")
                    }
                })
        }
    ) {
        if (state.error!=null){
            Text(text = "${state.error}", color = Color.Red, modifier = Modifier.fillMaxWidth())
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.fillParentMaxHeight()
                        .padding(bottom = 32.dp)
                ) {
                    LargeSpace()
                    TextInputWidget(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.name,
                        onValueChange = {
                            onEvent(NewProductEvent.OnNameChanged(it))
                        },
                        label = "Nom du produit",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.crayon_24),
                                contentDescription = ""
                            )
                        },
                    )
                    SmallSpace()
                    TextInputWidget(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.mark,
                        onValueChange = {
                            onEvent(NewProductEvent.OnMarlChanged(it))
                        },
                        label = "Mark",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.crayon_24),
                                contentDescription = ""
                            )
                        },
                    )


                    SmallSpace()

                        TextInputWidget(
                            modifier = Modifier.fillMaxWidth(),
                            value = "${state.priceAchat}",
                            onValueChange = {
                                onEvent(NewProductEvent.OnPriceAchatChanged(it.toDouble()))
                            },
                            label = "Prix d'achat",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.usd_cercle_24),
                                    contentDescription = ""
                                )
                            },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextInputWidget(
                            modifier = Modifier.fillMaxWidth(),
                            value = "${state.priceVente}",
                            onValueChange = {
                                onEvent(NewProductEvent.OnPriceVenteChanged(it.toDouble()))
                            }, label = "Prix de vente",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.usd_cercle_24),
                                    contentDescription = ""
                                )
                            })
                    SmallSpace()
                    TextInputWidget(
                        modifier = Modifier.fillMaxWidth(),
                        value = "${state.stock}",
                        onValueChange = {
                            onEvent(NewProductEvent.OnStockChanged(it.toInt()))
                        }, label = "Quantite",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.des_d6_24),
                                contentDescription = ""
                            )
                        })


                    Spacer(modifier = Modifier.height(56.dp))

                }
            }

            item {
                if (state.isLoading==true){
                    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){

                            CircularProgressIndicator(color = Color.Yellow)

                    }
                }else{
                ElevatedButton(
                    onClick = {
                        onEvent(NewProductEvent.OnSubmit)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10))
                        .height(64.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                    ),
                    shape = RoundedCornerShape(16),
                ) {
                    Text(text = "Enregistrer")
                }
                
                }
            }
        }
    }
}