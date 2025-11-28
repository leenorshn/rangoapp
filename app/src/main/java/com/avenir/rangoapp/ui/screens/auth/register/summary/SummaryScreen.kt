package com.avenir.rangoapp.ui.screens.auth.register.summary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenir.rangoapp.R
import com.avenir.rangoapp.core.LargeSpace
import com.avenir.rangoapp.core.SmallSpace
import com.avenir.rangoapp.ui.components.PrimaryButton
import com.avenir.rangoapp.ui.theme.FailureColor

@Composable
fun SummaryScreen(
    personalInfo: PersonalInfo,
    companyInfo: CompanyInfo,
    storeInfo: StoreInfo,
    isLoading: Boolean,
    error: String?,
    onPrevious: () -> Unit,
    onSubmit: () -> Unit,
) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.size(72.dp),
                    tint = Color.Yellow,
                )
                Text(
                    text = "Résumé de votre inscription",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Vérifiez vos informations avant de continuer",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            // Informations personnelles
            item {
                LargeSpace()
                Text(
                    text = "Informations personnelles",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Yellow
                )
                SmallSpace()
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                       // SummaryItem("Email", personalInfo.email)
                        SummaryItem("Nom", personalInfo.name)
                        SummaryItem("Téléphone", personalInfo.phone)
                    }
                }
            }
            
            // Informations de l'entreprise
            item {
                LargeSpace()
                Text(
                    text = "Informations de l'entreprise",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Yellow
                )
                SmallSpace()
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SummaryItem("Nom", companyInfo.name)
                        SummaryItem("Adresse", companyInfo.address)
                        SummaryItem("Téléphone", companyInfo.phone)
                    }
                }
            }
            
            // Informations du magasin
            item {
                LargeSpace()
                Text(
                    text = "Informations du magasin",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Yellow
                )
                SmallSpace()
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SummaryItem("Nom", storeInfo.name)
                        SummaryItem("Adresse", storeInfo.address)
                        SummaryItem("Téléphone", storeInfo.phone)
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
                if (error != null) {
                    Text(
                        text = error,
                        color = FailureColor,
                        fontSize = 12.sp
                    )
                    SmallSpace()
                }
                
                if (isLoading) {
                    CircularProgressIndicator(color = Color.Yellow)
                    SmallSpace()
                } else {
                    Row {
                        ElevatedButton(
                            modifier = Modifier.height(64.dp),
                            shape = RoundedCornerShape(16),
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary,
                            ),
                            onClick = { onPrevious() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.fleche_gauche_24),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Précédent")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        PrimaryButton(
                            label = "Créer le compte",
                            color = Color.Cyan
                        ) {
                            onSubmit()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

data class PersonalInfo(
    val name: String,
    val phone: String
)

data class CompanyInfo(
    val name: String,
    val address: String,
    val phone: String
)

data class StoreInfo(
    val name: String,
    val address: String,
    val phone: String
)

