package com.avenir.rangoapp.ui.screens.caisse

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.CaisseMenuItem
import com.avenir.rangoapp.ui.theme.CardColor
import com.avenir.rangoapp.ui.theme.FailureColor
import com.avenir.rangoapp.ui.theme.SuccessColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaisseScreen(
    onEnterClicked: () -> Unit,
    onSortieClicked: () -> Unit,
    onTransferClicked: () -> Unit,
    onAccountClicked:()->Unit,
    onSeeAllClicked:()->Unit,
) {
    var currency by remember {
        mutableStateOf("USD")
    }
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {

            },
            title = {
                Text("Caisse")
            },
            actions = {
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
            })
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            item {
                HorizontalDivider()
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Periode:")
                    Row {
                        Text(text = "Mois")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))


            }

            item {
                Box(
                    modifier = Modifier
                        .border(0.5.dp, color = Color.Gray, RoundedCornerShape(10))
                        .padding(20.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.statistiques_24),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(16.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column {
                            Text(text = "Current balance", color = Color.Gray, fontSize = 13.sp)
                            Text(text = "$ 1455.0", fontSize = 26.sp)
                        }
                        Column {
                            Text(text = "In", color = Color.Gray, fontSize = 13.sp)
                            Text(text = " $ 1465.0", color = SuccessColor)
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(text = "Out", color = Color.Gray, fontSize = 13.sp)
                            Text(text = " $ 10", color = FailureColor)
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CaisseMenuItem(
                        modifier = Modifier.weight(1f),
                        onClickedAction = {
                                          onEnterClicked()
                        },
                        color = SuccessColor,
                        icon = painterResource(
                            id = R.drawable.fleche_bas
                        ),
                        title = "Enter"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CaisseMenuItem(
                        modifier = Modifier.weight(1f),
                        onClickedAction = {
                                          onSortieClicked()
                        },
                        color = FailureColor,
                        icon = painterResource(
                            id = R.drawable.fleche_haut
                        ),
                        title = "Sortie"
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    OutlinedButton(onClick = { onAccountClicked() },
                        shape = RoundedCornerShape(16),
                        modifier = Modifier
                            .height(64.dp)
                            .weight(1f)) {
                        Text(text = "Accounts", color = MaterialTheme.colorScheme.onPrimary)
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(onClick = { onTransferClicked() },
                        shape = RoundedCornerShape(16),
                        modifier = Modifier
                            .height(64.dp)
                            .weight(1f)) {
                        Text(text = "Transfer", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
            item {
                SmallSpace()
                HorizontalDivider()
                SmallSpace()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Last trans", fontWeight = FontWeight.W600, color = Color.Gray)
                    OutlinedButton(onClick = {onSeeAllClicked() },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Gray,
                        )
                        ) {
                       Text(text = "See all")
                    }
                }
            }

            items(listOfTrans){ trans->
                Card(onClick = { /*TODO*/ },
                    colors = CardDefaults.cardColors(
                        containerColor = CardColor,
                    )) {
                    ListItem(
                        supportingContent = {
                             Text(text = trans.operation)
                        },
                        headlineContent = {
                                               Text(text = trans.libel)
                    }, leadingContent = {
                           Text(text = "$ ${trans.amount}")
                    }, trailingContent = {
                       val icon= if (trans.operation=="entre") painterResource(id = R.drawable.fleche_bas)
                            else painterResource(id = R.drawable.fleche_haut)
                        val tint=if (trans.operation=="entre") SuccessColor else FailureColor
                        Icon(icon,"",tint=tint)
                    })
                    HorizontalDivider()
                }
            }

            item {
                120.dp.Space()
            }
        }
    }
}