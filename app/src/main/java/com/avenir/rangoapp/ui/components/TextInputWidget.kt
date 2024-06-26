package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.ui.theme.GrayColor

@Composable
fun TextInputWidget(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    type: String = "text",
    label: String,
    leadingIcon:  @Composable() (() -> Unit)? = null
) {
    val keyboardType = if (type == "number") KeyboardType.Number else KeyboardType.Text
    Column {
        Text(text = label.split(" ").first())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedLabelColor = GrayColor,
            ),
            modifier = modifier

                .height(64.dp)
                .clip(
                    RoundedCornerShape(16)
                ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(label)
            },
            leadingIcon = leadingIcon,

            )
    }
}