package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.theme.FailureColor
import com.avenir.rangoapp.ui.theme.SuccessColor

@Composable
fun SettingMenuItem(onMenuClicked:()->Unit,name:String,icon:Painter,modifier: Modifier) {
    OutlinedCard(onClick = { onMenuClicked() }, modifier = modifier
        .padding(top = 8.dp)
        .height(160.dp)) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Icon(icon,"menu", modifier = Modifier.size(32.dp))
            10.dp.Space()
            Text(text = name, fontSize = 16.sp)
        }

    }
}