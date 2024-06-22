package com.avenir.rangoapp.ui.screens.settings.shop

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.models.CompanyModelExample
import com.avenir.rangoapp.ui.components.CustomButton
import com.avenir.rangoapp.ui.components.ToggleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopSettingsScreen(
    company:CompanyModel= CompanyModelExample
) {



    var shopName by remember {
        mutableStateOf(TextFieldValue(company.name))
    }

    var shopDomain by remember {
        mutableStateOf(TextFieldValue(company.domain))
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
                    onClick = {  },
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
                    onClick = {  },
                )
            }
        }
    }
}