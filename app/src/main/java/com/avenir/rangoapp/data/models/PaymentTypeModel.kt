package com.avenir.rangoapp.data.models

import androidx.compose.ui.graphics.Color

data class PaymentMethodModel(
    val id: Int,
    val name: String,
    val description: String,
    val paymentType: PaymentType,
    val color: Color
)


enum class PaymentType {
    PayAsYouGo,
    Monthly,
    Yearly,
}

val listPaymentMethods = listOf(
    PaymentMethodModel(
        id = 3,
        name = "$ 70.99 /Yearly",
        description = "Yearly",
        PaymentType.Yearly,
        color = Color.Yellow
    ),
    PaymentMethodModel(
        id = 2,
        name = "$ 7.99 /Monthly",
        description = "Monthly",
        PaymentType.Monthly,
        color = Color.White
    ),
    PaymentMethodModel(
        id =1,
        name = "Pay As You Go",
        description = "Pay As You Go",
        PaymentType.PayAsYouGo,
        color = Color.Transparent
    ),


    )