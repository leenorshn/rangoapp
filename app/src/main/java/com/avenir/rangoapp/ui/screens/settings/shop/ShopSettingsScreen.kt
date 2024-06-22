package com.avenir.rangoapp.ui.screens.settings.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.models.CompanyModelExample
import com.avenir.rangoapp.ui.components.CustomButton
import com.avenir.rangoapp.ui.components.ToggleTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopSettingsScreen(
    company: CompanyModel = CompanyModelExample
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    var isAddress by remember {
        mutableStateOf(false)
    }
    val modelState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var shopName by remember {
        mutableStateOf(TextFieldValue(company.name))
    }

    var shopAddress by remember {
        mutableStateOf(TextFieldValue(company.address))
    }

    var shopDomain by remember {
        mutableStateOf(TextFieldValue(company.domain))
    }

    var shopDescription by remember {
        if (isAddress) mutableStateOf(TextFieldValue(company.address)) else {
            mutableStateOf(TextFieldValue(company.description))
        }
    }


    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Shop settings") })
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            item {
                HorizontalDivider()
                Spacer(modifier = Modifier.height(32.dp))
                ToggleTextField(
                    name = shopName,
                    label = "Shop name",
                    onChange = { shopName = it },
                )
                LargeSpace()
                CustomButton(
                    name = company.address,
                    label = "Shop Address",
                    onClick = {
                        isAddress=true
                        scope.launch {
                            modelState.expand()
                        }.invokeOnCompletion {
                            openDialog = openDialog.not()
                        }
                    },
                )
                LargeSpace()
                ToggleTextField(
                    name = shopDomain,
                    label = "Shop Domaine",
                    onChange = { shopDomain = it },
                )
                LargeSpace()
                CustomButton(
                    name = company.description,
                    label = "Shop Description",
                    onClick = {
                        isAddress=false
                        scope.launch {
                            modelState.expand()
                        }.invokeOnCompletion {
                            openDialog = openDialog.not()
                        }
                    },
                )
            }
        }

        if (openDialog) {
            ModalBottomSheet(
                sheetState = modelState,
                onDismissRequest = {
                    scope.launch {
                        modelState.hide()
                    }.invokeOnCompletion {
                        openDialog = openDialog.not()
                    }
                }) {
                Column(
                    modifier = Modifier
                        .height(450.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val t = if (isAddress) "Adresse" else "Description"
                    //LargeSpace()
                    Text(text = "Modifier $t")
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = if (!isAddress) {
                            shopDescription
                        } else {
                            shopAddress
                        },
                        onValueChange = {
                            if (isAddress) {
                                shopAddress = it
                            } else {
                                shopDescription = it
                            }

                        },
                        placeholder = {
                            Text(text = "Entrer $t")
                        },
                        minLines = 2,
                        maxLines = 5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(16)
                            ),

                        )
                    Spacer(modifier = Modifier.height(80.dp))
                    ElevatedButton(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = {
                            // cdf=text.text
                            scope.launch {
                                modelState.hide()
                            }.invokeOnCompletion {
                                openDialog = openDialog.not()
                            }
                        }) {
                        Text(text = "Valider")
                    }
                }
            }
        }
    }
}