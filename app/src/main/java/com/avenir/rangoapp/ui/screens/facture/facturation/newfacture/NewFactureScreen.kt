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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.components.DatePickerWidget
import com.avenir.rangoapp.ui.theme.GrayColor
import com.avenir.rangoapp.ui.theme.PrimaryColor
import com.avenir.rangoapp.ui.theme.SecondaryColor
import com.avenir.rangoapp.ui.theme.SuccessColor

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFactureScreen() {
    var currency by remember {
        mutableStateOf("USD")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "New Invoice") })
        },
        bottomBar = {
            Column(modifier = Modifier.padding(bottom = 32.dp, start = 24.dp)) {
                Text(text = "Total:", color = Color.Gray)
                Text(text = "0.00 $currency", fontSize = 32.sp, fontWeight = FontWeight.W300)
                16.dp.Space()
                Row {
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Draft", modifier = Modifier.padding(vertical = 14.dp))
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    ElevatedButton(
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                        ),
                        onClick = { /*TODO*/ }, modifier = Modifier.weight(1f),
                    ) {
                        Text(text = "Save Invoice", modifier = Modifier.padding(vertical = 14.dp))
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "No:")
                Text(text = "F001/2024", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
            16.dp.Space()
            Divider()
            24.dp.Space()

            DatePickerWidget()

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
                    Box(modifier = Modifier
                        .background(
                            color = if (currency == "USD") SuccessColor else Color.White,
                            shape = RoundedCornerShape(20)
                        )
                        .border(1.dp, color = GrayColor, RoundedCornerShape(20))
                        .padding(10.dp)

                        .clickable {
                            currency = "USD"
                        }){
                        Text(text = "USD",  color = if (currency == "USD") PrimaryColor else SecondaryColor,)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier
                        .background(
                            color = if (currency == "CDF") SuccessColor else Color.White,
                            shape = RoundedCornerShape(20)
                        )
                        .padding(10.dp)
                        .clickable {
                            currency = "CDF"
                        }
                    ){
                        Text(text = "CDF", color = if (currency == "CDF") PrimaryColor else SecondaryColor,)
                    }
                }
            }
            16.dp.Space()
            Divider()
            20.dp.Space()
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "Customer")
                ElevatedButton(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                        )
                    ) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    Spacer(modifier = Modifier.width(8.dp))
                   Text(text = "Select client") 
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
                    checked = true, onCheckedChange = {},
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
                            color = Color.Gray.copy(
                                alpha = 1f
                            ), shape = RoundedCornerShape(10)
                        )
                        .clip(
                            RoundedCornerShape(20)
                        ),
                    contentAlignment = Alignment.Center,
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "Add product")
                    }
                }
            }
        }
    }
}