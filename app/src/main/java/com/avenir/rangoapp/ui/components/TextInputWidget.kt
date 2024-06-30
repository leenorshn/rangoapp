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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.ui.theme.GrayColor

@Composable
fun TextInputWidget(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon:  @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
) {
    Column {
        Text(text = label)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            placeholder = placeholder,
            keyboardOptions = keyboardOptions,
            //supportingText=supportingText,
            shape = TextFieldDefaults.shape,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = GrayColor,
                focusedIndicatorColor = Color.White,
            ),
            modifier = modifier

                .height(64.dp)
                .clip(
                    RoundedCornerShape(16)
                ),

            value = value,
            maxLines = 1,
            singleLine = true,
            onValueChange = onValueChange,
            leadingIcon = leadingIcon,

            )
        if (supportingText != null) {
            supportingText()
        }
    }
}