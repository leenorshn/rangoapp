package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R

@Composable
fun ToggleTextField(
    name: TextFieldValue,
    label: String,
    onChange: (TextFieldValue) -> Unit
) {

    var currentEdit by remember {
        mutableStateOf(false)
    }

    if (currentEdit) {
        TextField(
            value = name,
            onValueChange = onChange,
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
                        currentEdit = false
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
                    RoundedCornerShape(16)
                )
                .padding(vertical = 16.dp, horizontal = 8.dp),
            supportingContent = {
                Text(text = label,color = Color.Cyan, fontSize = 13.sp)
            },
            headlineContent = {
                val value = name.text.ifEmpty { "My shop $label" }
                Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }, trailingContent = {
                IconButton(onClick = {
                    currentEdit = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.crayon_24),
                        contentDescription = ""
                    )
                }
            })
    }
}

@Composable
fun CustomButton(
    label: String,
    name:String,
    onClick:()->Unit
) {
    ListItem(
        modifier = Modifier
            .border(
                1.dp,
                color = Color.Gray,
                RoundedCornerShape(16)
            )
            .padding(vertical = 16.dp, horizontal = 8.dp),
        supportingContent = {
            Text(text = label,color = Color.Cyan, fontSize = 13.sp)
        },
        headlineContent = {
            val value = name.ifEmpty { "My shop $label" }
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }, trailingContent = {
            IconButton(onClick =onClick) {
                Icon(
                    painter = painterResource(id = R.drawable.crayon_24),
                    contentDescription = ""
                )
            }
        })
}