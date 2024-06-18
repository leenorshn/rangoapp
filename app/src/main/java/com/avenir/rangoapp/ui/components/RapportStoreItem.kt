package com.avenir.rangoapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.RangoApp.R
import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.ui.theme.FailureColor
import com.avenir.rangoapp.ui.theme.SuccessColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RapportStoreItem(rapport: RapportStoreModel) {

        ListItem(
            headlineContent = {
                Text(text = rapport.productName, fontSize = 20.sp, fontWeight = FontWeight.W500)
            },
            supportingContent = {
                Text(text = rapport.date,  fontSize = 12.sp)
            },
            overlineContent = {
                val color=  if (rapport.type=="Entrer") SuccessColor else FailureColor
                Text(text = rapport.type,color=color)
            },
            leadingContent = {

            },
            trailingContent = {
             val painterResource =   if(rapport.type=="Entrer") painterResource(id = R.drawable.fleche_vers_le_bas_24)
                else painterResource(id = R.drawable.fleche_vers_le_haut_24)
              val color=  if (rapport.type=="Entrer") SuccessColor else FailureColor
                Row(
                    modifier = Modifier.width(80.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "${rapport.quantity} p", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource, contentDescription = "", tint = color)
                    }
                }
            })
        HorizontalDivider()
    }
