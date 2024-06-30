package com.avenir.rangoapp.ui.screens.auth.register.type

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.data.models.listOfCompanyType

@Composable
fun TypeScreen(
    onTypeSelected: (String) -> Unit,
) {
   Scaffold {
       LazyColumn(modifier = Modifier.padding(it)) {
           items(listOfCompanyType){item->
               OutlinedCard(onClick = { /*TODO*/ }) {
                   ListItem(
                       leadingContent = {
                           Image(
                               painter = painterResource(id = item.image),
                               contentDescription = null,
                               modifier = Modifier.size(64.dp)
                           )
                       },
                       headlineContent = {
                           Text(text = item.type)
                       },
                       supportingContent = {
                           Text(text = item.description)
                       },
                       trailingContent = {
                           Text(text = item.productLabel)
                       },
                       modifier = Modifier.padding(16.dp),
                      )
               }
           }
       }
   }
}