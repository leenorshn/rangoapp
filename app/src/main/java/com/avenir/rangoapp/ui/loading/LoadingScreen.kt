package com.avenir.rangoapp.ui.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo), contentDescription = "",
                tint = Color.Yellow, modifier = Modifier.size(72.dp)
            )
            Spacer(modifier=Modifier.height(72.dp))
            Text(
                text = "Dooka",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
            Spacer(modifier=Modifier.height(72.dp))
            LargeSpace()
            LargeSpace()
            LargeSpace()
            CircularProgressIndicator(modifier = Modifier.size(40.dp), color = Color.Yellow)
        }
    }
}