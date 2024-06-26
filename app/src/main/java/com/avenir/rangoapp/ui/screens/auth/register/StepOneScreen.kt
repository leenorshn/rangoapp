package com.avenir.rangoapp.ui.screens.auth.register

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.components.TextInputWidget


@Composable
fun StepOneScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {

    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Etape", fontSize = 12.sp)
                Text(text = "1/4", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Identification", fontSize = 24.sp)
            }
            item {
                LargeSpace()
                TextInputWidget(
                    value = state.name,
                    onValueChange = {
                        onEvent(RegisterEvent.NameChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Nom d'entreprise",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Create,
                            contentDescription = ""
                        )
                    }

                )
                SmallSpace()
                TextInputWidget(
                    value = state.type,
                    onValueChange = {
                        onEvent(RegisterEvent.TypeChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Type d'entreprise",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Create,
                            contentDescription = ""
                        )
                    }

                )
                SmallSpace()
                TextInputWidget(
                    value = state.city,
                    onValueChange = {
                        onEvent(RegisterEvent.CityChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Ville ",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = ""
                        )
                    }

                )
                SmallSpace()
                TextInputWidget(
                    value = state.address,
                    onValueChange = {
                        onEvent(RegisterEvent.AddressChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Adresse /numero",
                    leadingIcon = {
                        Icon(
                           Icons.Outlined.LocationOn,
                            contentDescription = ""
                        )
                    }

                )

            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                Row {
                    ElevatedButton(
                        modifier = Modifier.height(64.dp),
                        shape = RoundedCornerShape(16),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        onClick = { onPrevious() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.fleche_gauche_24),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Prec.")
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                    PrimaryButton(label = "Suivant") {
                        onNext()
                    }
                }
            }
        }
    }

}

