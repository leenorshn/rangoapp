package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.models.toFactureModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject

class VenteDataSource @Inject constructor(
   private val database:Databases,
   private val account:Account
) {
    // save facture

    suspend fun createFacture(
         products:List<String>,
         client:String,
         quantity:Int,
         price:Double,
         date:String,
         currency:String
    ):Boolean{
        val session = account.getSession("current")
        database.createDocument(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "",
            documentId = ID.unique(),
            data = mapOf(
                "products" to products,
                "client" to client,
                "quantity" to quantity,
                "price" to price,
                "currency" to currency,
                "date" to date,
                "company" to session.userId
            )
        )
        products.forEach { it
            RapportStoreDataSource(account = account, database = database)
                .createRapport(product =it, quantity = quantity, type = "OUT")
        }
        return true
    }
    // getAll factures
    suspend fun getFactures():List<FactureModel>{
        val session = account.getSession("current")
        val res=database.listDocuments(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "",
            queries = listOf(
                Query.equal("company",session.userId),
                Query.orderDesc("\$createdAt")
            )
        )
        val factures=res.documents.map {
            it.toFactureModel()
        }
        return factures
    }
}