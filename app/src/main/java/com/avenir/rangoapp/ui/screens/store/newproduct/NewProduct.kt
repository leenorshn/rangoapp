package com.avenir.rangoapp.ui.screens.store.newproduct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.TextInputWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductScreen(onSaveClicked: () -> Unit) {

    var clientName = TextFieldValue(text = "")
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Nouveau produit")
            },
                actions = {
                    TextButton(onClick = { /*TODO*/ },

                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.onTertiary,
                            contentColor = MaterialTheme.colorScheme.tertiary,
                        ),) {
                       Text(text = "Enregistrer") 
                    }
                })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight()
                        .padding(bottom = 32.dp)
                ) {
                    LargeSpace()
                    TextInputWidget(
                        modifier = Modifier.fillMaxWidth(),
                        value = clientName, onValueChange = {
                        clientName = it
                    }, label = "Product name",
                        leadingIcon = painterResource(id = R.drawable.crayon_24),
                        )
                    SmallSpace()
                    TextInputWidget( modifier = Modifier.fillMaxWidth(),
                        value = clientName, onValueChange = {
                        clientName = it
                    }, label = "Product Mark",
                        leadingIcon = painterResource(id = R.drawable.crayon_24),
                        )
                    SmallSpace()
                    TextInputWidget(
                        leadingIcon = painterResource(id = R.drawable.crayon_24),
                        modifier = Modifier.fillMaxWidth(),
                        value = clientName, onValueChange = {
                        clientName = it

                    }, label = "Categorie")
                    SmallSpace()
                    Row {
                        TextInputWidget(
                            modifier = Modifier.weight(1f),
                            value = clientName, onValueChange = {
                            clientName = it
                        }, label = "Prix d'achat",
                            type = "number",
                            leadingIcon = painterResource(id = R.drawable.usd_cercle_24)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextInputWidget(
                            modifier = Modifier.weight(1f),
                            value = clientName, onValueChange = {
                            clientName = it
                        }, label = "Prix de vente",
                            type = "number",
                            leadingIcon = painterResource(id = R.drawable.usd_cercle_24))
                    }
                    SmallSpace()

                    SmallSpace()
                    TextInputWidget(
                        modifier = Modifier.fillMaxWidth(),
                        value = clientName, onValueChange = {
                        clientName = it
                    }, label = "Charge",type = "number",
                        leadingIcon = painterResource(id = R.drawable.usd_cercle_24))
                    Spacer(modifier = Modifier.weight(1f))
                    ElevatedButton(
                        onClick = onSaveClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                        )
                    ) {
                        Text(text = "Enregistrer")
                    }
                }
            }
            
                
            
        }
    }
}