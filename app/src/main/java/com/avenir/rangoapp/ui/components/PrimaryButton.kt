package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    label:String,
    onClick: () -> Unit
) {
    ElevatedButton(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.Yellow,
            contentColor = Color.Black
        ),
        onClick = onClick) {
        Text(text = label, fontWeight = FontWeight.SemiBold)
    }
}