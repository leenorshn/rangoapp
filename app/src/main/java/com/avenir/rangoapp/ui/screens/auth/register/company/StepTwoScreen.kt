package com.avenir.rangoapp.ui.screens.auth.register.company

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
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
import com.avenir.rangoapp.ui.components.DropDownMenuItem
import com.avenir.rangoapp.ui.theme.FailureColor


@Composable
fun StepTwoScreen(
    state: ViewState,
    onEvent: (CompanyEvent) -> Unit,
    onNext: () -> Unit,
) {
    var error = remember { mutableStateOf("") }
    val companyTypes = listOf("Type", "Commerce", "SARL", "SA", "SNC", "Autre")
    val selectedTypeIndex = remember { 
        mutableStateOf(
            if (state.type.isNotEmpty()) {
                companyTypes.indexOf(state.type).takeIf { it > 0 } ?: 0
            } else {
                0
            }
        )
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
                    painter = painterResource(id = R.drawable.logo), 
                    contentDescription = "",
                    modifier = Modifier.size(72.dp),
                    tint = Color.Yellow,
                )
                Text(text = "New Company", fontSize = 24.sp)
            }
            item {
                LargeSpace()
                TextInputWidget(
                    value = state.name,
                    onValueChange = {
                        onEvent(CompanyEvent.NameChanged(it))
                    },
                    placeholder = {
                        Text(text = "Nom")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Nom",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Create,
                            contentDescription = ""
                        )
                    }
                )
                SmallSpace()
                TextInputWidget(
                    value = state.email,
                    onValueChange = {
                        onEvent(CompanyEvent.EmailChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    placeholder = {
                        Text(text = "Email")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = ""
                        )
                    }
                )
                SmallSpace()
                TextInputWidget(
                    value = state.phone,
                    onValueChange = {
                        onEvent(CompanyEvent.PhoneChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Some phone",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    placeholder = {
                        Text(text = "Téléphone")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Phone,
                            contentDescription = ""
                        )
                    }
                )
                SmallSpace()
                // Dropdown for Type
                Column {
                    Text(text = "Type", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    DropDownMenuItem(
                        itemList = companyTypes,
                        selectedIndex = selectedTypeIndex.value,
                        modifier = Modifier.fillMaxWidth(),
                        onItemClick = { index ->
                            if (index > 0) { // Skip "Type" placeholder
                                selectedTypeIndex.value = index
                                onEvent(CompanyEvent.TypeChanged(companyTypes[index]))
                            }
                        }
                    )
                }
            }
            item {
                if (state.error != null) {
                    Text(text = state.error, color = FailureColor, fontSize = 12.sp)
                }
                if (error.value.isNotEmpty()) {
                    Text(text = error.value, color = FailureColor, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(50.dp))
                if (state.isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(color = Color.Yellow)
                    SmallSpace()
                } else {
                    PrimaryButton(label = "Creer") {
                        if (state.name.length > 2 && state.phone.length > 8) {
                            onEvent(CompanyEvent.OnSubmit)
                        } else {
                            error.value = "Veuillez remplir tous les champs correctement"
                        }
                    }
                }
            }
        }
    }
}