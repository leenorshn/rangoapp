package com.avenir.rangoapp.ui.screens.settings.users

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.Space

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onNewUserClicked:()->Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Mes agents")
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { onNewUserClicked() },
                modifier = Modifier.height(64.dp),
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.ajout_dutilisateur_24),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Ajouter utilisateur")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                24.dp.Space()
                HorizontalDivider()
            }
            items(listOfUser) { user ->
                Card(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 16.dp)) {
                    ListItem(headlineContent = {

                        Text(text = user.name)
                    },
                        supportingContent = {
                            Text(text = "${user.phone} ")
                        }, overlineContent = {
                            val color = if (user.role == "Admin") Color.Cyan else Color.Yellow
                            Row {
                                Text(text = "Role: ")
                                Text(text = " ${user.role}", color = color)
                            }
                        },
                        trailingContent = {
                            val icon =
                                if (user.role == "Admin") Icons.Outlined.Lock else Icons.Outlined.Create
                            Icon(imageVector = icon, contentDescription = "")
                        }
                    )
                }
                HorizontalDivider()
            }
        }
    }
}