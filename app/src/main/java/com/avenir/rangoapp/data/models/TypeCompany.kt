package com.avenir.rangoapp.data.models

import com.avenir.rangoapp.R

data class TypeCompany(
    val type:String,
    val image:Int,
    val description:String,
    val productLabel:String,
    val productUnit:List<String>,
)



val listOfCompanyType = listOf(
    TypeCompany(
        type = "Vente Electronique",
        image = R.drawable.a,
        description = "Vente de produit électronique, telephones , ordinateur, etc",
        productLabel = "Appareil",
        productUnit = listOf(
            "Carton",
            "Piece"
        )
    ),
    TypeCompany(
        type = "Boucherie et charcuterie",
        image = R.drawable.c,
        description = "Vente de produit électronique, telephones , ordinateur, etc",
        productLabel = "Viande",
        productUnit = listOf(
            "Kg",
        )
    ),
    TypeCompany(
        type = "Restaurant",
        image = R.drawable.d,
        description = "Vente de habits, parfum ,etc",
        productLabel = "Plat",
        productUnit = listOf(
            "Piece",
            "SIZAIN",
            "DOUZAIN"
        )
    ),
    TypeCompany(
        type = "Bar ou Club",
        image = R.drawable.b,
        description = "Vente de produit alcoolique et non alcoolique etc",
        productLabel = "Boisson",
        productUnit = listOf(
            "Bouteille",
            "Caisse",
            "Demi-Caisse",
            "Cannette",
        )

    )
)


