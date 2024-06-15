package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.ui.theme.GrayColor

@Composable
fun ProviderItem(modifier: Modifier = Modifier) {
    Column {
        ListItem(
            leadingContent = {
                Box(
                    modifier = Modifier
                        .border(1.dp, GrayColor, shape = RoundedCornerShape(50)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "",
                        modifier = Modifier.padding(16.dp),)
                }
            },
            headlineContent = {
                Text("Victor Katembo")
            },
            supportingContent = {
                Text(text = "+243978154329")
            }
            ,
            trailingContent = {
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "")
            }
        )
        HorizontalDivider()
    }
}