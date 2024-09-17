package com.avenir.rangoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.ui.theme.GrayColor

@Composable
fun ProductItem(product: ProductModel) {
    Column {
        ListItem(
            headlineContent = {
                Text(text = product.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.W500,)
            },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.mallette_24),
                    contentDescription =null
                )
            },
            trailingContent = {
                Column {
                    IconButton(onClick = { /*TODO*/ },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White.copy(
                                alpha = 0.2f
                            )
                        )
                        ) {
                        Icon(
                            painterResource(id = R.drawable.crayon_24),
                            contentDescription =null,
                            tint = GrayColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "${product.stock}", color = Color.Yellow)
                }
            },
            supportingContent = {
                Column {
                   var text= buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Cyan, fontWeight = FontWeight.SemiBold)) {
                            append("${product.priceVente} $ ")
                        }


                        withStyle(style = SpanStyle(fontWeight = FontWeight.W300, color = Color.Cyan, fontSize = 12.sp)) {
                            append("Prix de vente")
                        }

                    }

                    Text(text = text)
                    Text(text = "$ ${product.priceAchat} Prix d'achat   ",color= Color.Gray, fontSize = 12.sp)
                }
            },

        )
        Spacer(modifier = Modifier.size(10.dp))
        HorizontalDivider()
    }
}