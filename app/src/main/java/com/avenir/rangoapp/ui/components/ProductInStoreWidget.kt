package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.avenir.RangoApp.R

@Composable
fun ProductInStoreWidget() {
    Column {
        ListItem(
            headlineContent = {
                Text(text = "Lenovo T470", fontSize = 20.sp)
            },
            supportingContent = {
                Text(text = "12 pieces", color = Color.Cyan, fontSize = 12.sp)
            },
            overlineContent = {
                Text(text = "Mark: Ordinateur")
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.mallette_24),
                    contentDescription = ""
                )
            },
            trailingContent = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = "")
                }
            })
        HorizontalDivider()
    }
}