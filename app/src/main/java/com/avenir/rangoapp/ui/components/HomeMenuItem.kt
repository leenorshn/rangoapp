package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.ui.theme.GrayColor

@Composable
fun HomeMenuItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    onTapMenu: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = GrayColor, shape = RoundedCornerShape(16))
            .padding(24.dp)
            .clickable {
                onTapMenu()
            }
    ){
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
           ) {
            Icon(icon, contentDescription ="icon", modifier = Modifier.size(24.dp) )
           Spacer(modifier = Modifier.width(40.dp))
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.W400)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Outlined.KeyboardArrowRight, contentDescription = "", tint = GrayColor)

        }
    }
}

