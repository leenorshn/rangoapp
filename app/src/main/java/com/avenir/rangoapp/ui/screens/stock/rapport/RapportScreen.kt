package com.avenir.rangoapp.ui.screens.stock.rapport

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.ui.components.RapportStoreItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RapportStoreScreen(
    state: RapportStoreState?,
    //onProviderClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Mouvement de stock")
            },
                actions = {
//                    ElevatedButton(onClick = { onProviderClicked() },
//                        colors = ButtonDefaults.elevatedButtonColors(
//                            containerColor = MaterialTheme.colorScheme.tertiary,
//                            contentColor = MaterialTheme.colorScheme.onTertiary
//                        )
//                        ) {
//                      Icon(painter = painterResource(id = R.drawable.ic_friends),"",
//                          modifier = Modifier.size(16.dp))
//                      Spacer(modifier = Modifier.width(8.dp))
//                      Text(text = "Providers")
//
//                    }
                })
        },

    ) {
        if (state?.isLoading == true) {
            LinearProgressIndicator(
                color = Color.Yellow,
                modifier = Modifier.fillMaxWidth().padding(it)
            )
        }
        if (state?.error != null) {
            Text(text = "${state.error}", modifier = Modifier.padding(it).fillMaxWidth())
        } else{
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.size(16.dp))
                }
                items(state?.rapports.orEmpty()) { rapport ->
                    RapportStoreItem(rapport = rapport)
                }
                item {
                    Spacer(modifier = Modifier.size(120.dp))
                }
            }
        }
    }
}