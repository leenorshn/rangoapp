package com.avenir.rangoapp.ui.screens.auth.register.company

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun StepTwoScreen(
    state: ViewState,
    onEvent: (CompanyEvent) -> Unit,
    onNext: () -> Unit,
) {
    
    var error= remember {
        mutableStateOf("")
    }
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

                Icon(
                    painter = painterResource(id = R.drawable.logo), contentDescription = "",
                    modifier = Modifier.size(72.dp),
                    tint = Color.Yellow,
                )
                Text(text = "Identité de l'entreprise", fontSize = 24.sp)
            }
            item {
                LargeSpace()
                TextInputWidget(
                    value = state.name,
                    onValueChange = {
                        onEvent(CompanyEvent.NameChanged(it))
                    },
                    placeholder = {
                        Text(text = "Nom de l'entreprise")
                    },
                    supportingText = {
                        Text(
                            text = "Exemple: Dooka-Shop",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Nom de l'entreprise",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Create,
                            contentDescription = ""
                        )
                    }

                )
                SmallSpace()
                TextInputWidget(
                    value = state.address,
                    onValueChange = {
                        onEvent(CompanyEvent.AddressChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Adresse de l'entreprise",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = ""
                        )
                    },
                    placeholder = {
                        Text(text = "Adresse")
                    },
                    supportingText = {
                        Text(
                            text = "Exemple: Butembo/Rue-Kin/GTB N=32",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                )


            }

            item {
                Text(text = error.value,color= FailureColor, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(140.dp))



                PrimaryButton(label = "Suivant") {
                    if (state.name.length>2&&state.address.length>6) {
                        onNext()
                    }else{
                        error.value="Veuillez remplir tous les champs"
                    }
                }

            }
        }
    }
}