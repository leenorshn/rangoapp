package com.avenir.rangoapp.ui.screens.auth.register.company

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.components.TextInputWidget
import com.avenir.rangoapp.ui.theme.FailureColor


@Composable
fun StepFinalScreen(
    state: ViewState,
    onEvent: (CompanyEvent) -> Unit,
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

                Text(text = "Contacts", fontSize = 24.sp)
            }
            item {
                LargeSpace()
                TextInputWidget(
                    value = state.phone,
                    onValueChange = {
                        onEvent(CompanyEvent.RccmChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Phone de l'entreprise",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Create,
                            contentDescription = ""
                        )
                    }

                )

                LargeSpace()
                TextInputWidget(
                    value = state.email,
                    onValueChange = {
                        onEvent(CompanyEvent.RccmChanged(it))
                    },
                    supportingText = {
                        Text(
                            text = "Exemple: mclean@gmail.com",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Email de l'entreprise",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Create,
                            contentDescription = ""
                        )
                    }

                )

            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                if (!state.isLoading){
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
                        PrimaryButton(label = "Terminer", color = Color.Cyan) {
                            onEvent(CompanyEvent.OnSubmit)
                        }
                    }
                }else{
                    CircularProgressIndicator(color = Color.Yellow)
                }
                SmallSpace()
                if (state.error!=null){
                    Text(text = state.error,color= FailureColor)
                }
            }
        }
    }
}