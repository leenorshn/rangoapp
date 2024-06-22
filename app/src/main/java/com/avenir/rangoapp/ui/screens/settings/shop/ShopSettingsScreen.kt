package com.avenir.rangoapp.ui.screens.settings.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.avenir.RangoApp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopSettingsScreen(

) {

    var isShopNameEdit by remember {
        mutableStateOf(false)
    }

    var shopName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Shop settings") })
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            item {
                if (isShopNameEdit) {
                    TextField(
                        value = shopName,
                        onValueChange = {
                            shopName = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(
                                RoundedCornerShape(16)
                            ),
                        trailingIcon = {
                            IconButton(
                                colors = IconButtonColors(
                                    containerColor = Color.Yellow,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = Color.Gray,
                                    disabledContentColor = Color.White
                                ),
                                modifier = Modifier.padding(end = 8.dp),
                                onClick = {
                                isShopNameEdit = false
                            }) {
                                Icon(Icons.Filled.Done, "")
                            }
                        }
                    )
                } else {
                    ListItem(
                        modifier = Modifier
                            .border(
                                1.dp,
                                color = Color.Gray,
                                RoundedCornerShape(20)
                            )
                            .height(80.dp),
                        headlineContent = {
                            val name = shopName.text.ifEmpty { "Shop name" }
                            Text(text = name)
                        }, trailingContent = {
                            IconButton(onClick = {
                                isShopNameEdit = true
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.crayon_24),
                                    contentDescription = ""
                                )
                            }
                        })
                }
            }
        }
    }
}