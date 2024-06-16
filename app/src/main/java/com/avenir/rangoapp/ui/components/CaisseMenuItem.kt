package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.core.Space

@Composable
fun CaisseMenuItem(
    modifier: Modifier=Modifier,
    onClickedAction: () -> Unit,
    color: Color,
    icon: Painter,
    title: String,

    ) {
    Box(
        modifier = modifier
            .height(64.dp)
            .background(color = color, RoundedCornerShape(16))
            .clickable {
                onClickedAction()
            },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(painter = icon, contentDescription = " symbol")
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontWeight = FontWeight.W400)
        }
    }
}