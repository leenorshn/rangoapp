package com.avenir.rangoapp.ui.screens.caisse.enter

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
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.avenir.rangoapp.ui.theme.GrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterScreen(
    onSaveClicked:()->Unit
) {
    var currency by remember {
        mutableStateOf("USD")
    }
    var clientName= TextFieldValue(text = "")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Entrée en caisse") })
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(horizontal = 24.dp)) {
            40.dp.Space()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment =  Alignment.CenterVertically
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
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = GrayColor,
                ),
                modifier = Modifier.fillMaxWidth().height(64.dp).clip(
                    RoundedCornerShape(16)
                ),
                value = clientName, onValueChange = {value->
                    clientName=value
                },
                label = {
                    Text("Personne de reference")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
                },

                )
            20.dp.Space()
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = GrayColor,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier.fillMaxWidth().height(64.dp).clip(
                    RoundedCornerShape(16)
                ),
                value = clientName, onValueChange = {value->
                    clientName=value
                },
                label = {
                    Text("Montant")
                },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.dollar_24), contentDescription = "",
                        modifier = Modifier.size(16.dp))
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
                value = clientName, onValueChange = {value->
                    clientName= value
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
                    onSaveClicked()
                }) {
                Text(text = "Enregistrer", fontSize=18.sp, modifier = Modifier.padding(vertical = 14.dp))
            }
            40.dp.Space()
        }
    }
}