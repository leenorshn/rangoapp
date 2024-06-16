package com.avenir.rangoapp.ui.screens.facture.client.newClient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.ui.theme.GrayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewClientScreen(
    onSaveClicked:()->Unit
) {
    var clientName=TextFieldValue(text = "")
   Scaffold(
       topBar = {
           CenterAlignedTopAppBar(title = { Text(text = "New client") })
       }
   ) {
       Column(modifier = Modifier
           .padding(it)
           .padding(horizontal = 24.dp)) {
           40.dp.Space()
           TextField(
               colors = TextFieldDefaults.colors(
                   focusedLabelColor = GrayColor,
               ),
               modifier = Modifier.fillMaxWidth().height(64.dp).clip(
                   RoundedCornerShape(16)
               ),
               value = clientName, onValueChange = {value->
               clientName=value
           },
               label = {
                   Text("Client name")
               },
               leadingIcon = {
                   Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
               },

           )
           20.dp.Space()
           TextField(
               colors = TextFieldDefaults.colors(
                   focusedLabelColor = GrayColor,
               ),
               modifier = Modifier.fillMaxWidth().height(64.dp).clip(
                   RoundedCornerShape(16)
               ),
               value = clientName, onValueChange = {value->
               clientName=value
           },
               label = {
                   Text("Client phone")
               },
               leadingIcon = {
                   Icon(imageVector = Icons.Outlined.Phone, contentDescription = "")
               },

               )
           20.dp.Space()
           TextField(
               colors = TextFieldDefaults.colors(
                   focusedLabelColor = GrayColor,
               ),
               modifier = Modifier.fillMaxWidth().height(64.dp).clip(
                   RoundedCornerShape(16)
               ),
               value = clientName, onValueChange = {value->
               clientName= value
           },
               label = {
                   Text("Client address")
               },
               leadingIcon = {
                   Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "")
               },

               )

           Spacer(modifier = Modifier.weight(1f))
           ElevatedButton(
               shape = RoundedCornerShape(16),
               colors = ButtonDefaults.elevatedButtonColors(
                   contentColor = MaterialTheme.colorScheme.onTertiary,
                   containerColor = MaterialTheme.colorScheme.tertiary,
               ),
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(vertical = 20.dp),
               onClick = {
                   onSaveClicked()
               }) {
               Text(text = "Save client", fontSize=18.sp, modifier = Modifier.padding(vertical = 14.dp))
           }
           40.dp.Space()
       }
   }
}