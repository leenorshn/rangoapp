package com.avenir.rangoapp.ui.screens.settings.users

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.Space
import com.avenir.rangoapp.data.models.UserModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    state: UsersState,
    onNewUserClicked: () -> Unit,
    onEvent: (UsersEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.showSuccessMessage) {
        if (state.showSuccessMessage) {
            scope.launch {
                snackbarHostState.showSnackbar("Action réussie")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Mes agents")
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onNewUserClicked() },
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
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                24.dp.Space()
                if (state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Yellow,
                    )
                }
                if (state.error != null) {
                    Text(
                        text = state.error,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                HorizontalDivider()
            }
            if (!state.users.isNullOrEmpty()) {
                items(state.users) { user ->
                    UserItem(
                        user = user,
                        onDelete = { onEvent(UsersEvent.OnDeleteUser(user.id)) },
                        onBlock = { onEvent(UsersEvent.OnBlockUser(user.id)) },
                        onUnblock = { onEvent(UsersEvent.OnUnblockUser(user.id)) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: UserModel,
    onDelete: () -> Unit,
    onBlock: () -> Unit,
    onUnblock: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val roleColor = if (user.role == "Admin") Color.Cyan else Color.Yellow
    val isBlocked = user.isBlocked

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ListItem(
            headlineContent = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text(text = user.name)
                    if (isBlocked) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(Bloqué)",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            supportingContent = {
                Text(text = user.phone)
            },
            overlineContent = {
                Row {
                    Text(text = "Role: ")
                    Text(text = user.role, color = roleColor)
                }
            },
            trailingContent = {
                Row {
                    if (user.role == "Admin") {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Admin",
                            tint = Color.Cyan
                        )
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        if (isBlocked) {
                            DropdownMenuItem(
                                text = { Text("Débloquer") },
                                onClick = {
                                    onUnblock()
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = null
                                    )
                                }
                            )
                        } else {
                            DropdownMenuItem(
                                text = { Text("Bloquer") },
                                onClick = {
                                    onBlock()
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Lock,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                        DropdownMenuItem(
                            text = { Text("Supprimer", color = Color.Red) },
                            onClick = {
                                onDelete()
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}