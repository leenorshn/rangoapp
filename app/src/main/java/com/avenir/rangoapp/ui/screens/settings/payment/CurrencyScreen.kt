package com.avenir.rangoapp.ui.screens.settings.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.Space
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen() {
    var cdf by remember {
        mutableStateOf("2800")
    }
    var text by remember {
        mutableStateOf(TextFieldValue(cdf))
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    val modelState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Taux d'echange")
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider()
            LargeSpace()
            LargeSpace()
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 40.sp, fontWeight = FontWeight.W300)) {
                    append("1")
                }

                withStyle(style = SpanStyle(fontSize = 24.sp)) {
                    append("   USD")
                }
            })
            24.dp.Space()
            Text(text = "=", fontSize = 50.sp)
            24.dp.Space()
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 40.sp, fontWeight = FontWeight.W300)) {
                    append(cdf)
                }

                withStyle(style = SpanStyle(fontSize = 24.sp)) {
                    append("  CDF")
                }
            })
            LargeSpace()
            TextButton(
                onClick = {
                    scope.launch {
                        modelState.expand()
                    }.invokeOnCompletion {
                        openDialog= true
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = MaterialTheme.colorScheme.onTertiary,

                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.crayon_24),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Changer")
            }
        }
    }

    if (openDialog){
        ModalBottomSheet(
            sheetState = modelState,
            onDismissRequest = { openDialog.not() }) {
            Column(modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                LargeSpace()
                Text(text = "Defini le taux")
                Spacer(modifier = Modifier.height(32.dp))
                TextField(value =text , onValueChange ={
                    text=it
                } ,
                    trailingIcon = {
                        Text(text = "CDF")
                    },
                    modifier = Modifier.fillMaxWidth().height(64.dp)
                        .clip(
                            RoundedCornerShape(16)
                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    )
                Spacer(modifier = Modifier.height(64.dp))
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    shape = RoundedCornerShape(16),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = {
                    cdf=text.text
                    scope.launch {
                        modelState.hide()
                    }.invokeOnCompletion {
                      openDialog= openDialog.not()
                    }
                }) {
                    Text(text = "Valider")
                }
            }
        }
    }

}