package com.avenir.rangoapp.ui.screens.settings.users

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.Space

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(modifier: Modifier = Modifier) {
    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = "Mes agents")
            })
        }
    ){
        LazyColumn(modifier = Modifier.padding(it)) {
            item { 
                24.dp.Space()
            }
            items(listOfUser){user->
               Card(onClick = { /*TODO*/ }) {
                   ListItem(headlineContent = {
                       Text(text = user.name)
                   },
                       supportingContent = {
                           Text(text = "${user.phone} ")
                       }, overlineContent = {
                           Text(text = "Role: ${user.role}")
                       },
                       trailingContent = {
                           Text(text = "")
                       }
                       )
               } 
            }
        }
    }
}