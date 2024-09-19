package com.avenir.rangoapp.ui.screens.settings.shop

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.avenir.rangoapp.MainActivity
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.data.datasource.NotificationHandler
import okhttp3.internal.notify

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopSettingsScreen(
    state:ShopState,
    onEvent:(e:ShopEvent)->Unit,
) {
    val context= LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Shop settings") })
    }) {
        if (state.isLoading){
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                color = Color.Yellow
            )
        }
        if (state.error!=null){
            Text(text = state.error, color = Color.Red, modifier = Modifier.padding(it))
        }
        if (state.shop!=null){
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    //.fillMaxHeight()
            ) {
                item {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(text = "Nom")
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = state.shop.name,
                        onValueChange = {
                            onEvent(ShopEvent.OnNameChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(
                                RoundedCornerShape(16)
                            ),
                        placeholder = {
                            Text(text = "Nom")
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Edit, contentDescription = "")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                    )

                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Contact")
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = state.shop.phone,
                        onValueChange = {
                            onEvent(ShopEvent.OnPhoneChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(
                                RoundedCornerShape(16)
                            ),
                        placeholder = {
                            Text(text = "Telephone")
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Phone, contentDescription = "")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                        ),
                    )
                }
                item{
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Adresse")
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = state.shop.address,
                        onValueChange = {
                            onEvent(ShopEvent.OnAddressChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(
                                RoundedCornerShape(16)
                            ),
                        placeholder = {
                            Text(text = "Adresse")
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Edit, contentDescription = "")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "RCCM")
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = "${state.shop.rccm}",
                        onValueChange = {
                            onEvent(ShopEvent.OnRccmChanged(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(
                                RoundedCornerShape(16)
                            ),
                        placeholder = {
                            Text(text = "RCCM")
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Edit, contentDescription = "")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                    )
                }
                item { 
                    Spacer(modifier = Modifier.height(72.dp))
                    OutlinedCard(onClick = {
                        val notificationHandler = NotificationHandler(context)
                        notificationHandler.showSimpleNotification()
                    },
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        ) {
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 28.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,){
                            Icon(
                                painter = painterResource(id = R.drawable.save),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Enregistrer", fontWeight = FontWeight.SemiBold,)
                        }
                    }
                }
            }
            
        }



    }
    
}