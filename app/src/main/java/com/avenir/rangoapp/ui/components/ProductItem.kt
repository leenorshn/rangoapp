package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.ui.theme.FailureColor

@Composable
fun ProductItem(index:Int) {
    Column {
        ListItem(
            headlineContent = {
                Text(text = "Product $index")
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.mallette_24),
                    contentDescription =null
                )
            },
            trailingContent = {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription =null,
                    tint = FailureColor,
                )
            },
            supportingContent = {
                Text(text = "$ 20")
            },
            modifier = Modifier
                .border(
                    1.dp, Color.Gray, shape = RoundedCornerShape(10)
                )
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
    }
}