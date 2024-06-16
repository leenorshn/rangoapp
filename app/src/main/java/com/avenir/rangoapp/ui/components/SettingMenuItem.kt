package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.ui.theme.FailureColor
import com.avenir.rangoapp.ui.theme.SuccessColor

@Composable
fun SettingMenuItem(onMenuClicked:()->Unit,name:String,icon:Painter) {
    OutlinedCard(onClick = { onMenuClicked() }, modifier = Modifier.padding(top=8.dp)) {
        ListItem(headlineContent = {
            Text(text = name, fontSize = 24.sp)
        },
            leadingContent = {
                Icon(icon,"menu")
            }, trailingContent = {
                Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight,"")
            })
    }
}