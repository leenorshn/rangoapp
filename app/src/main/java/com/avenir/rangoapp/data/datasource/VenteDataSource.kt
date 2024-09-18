package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.FactureModel
import com.avenir.rangoapp.data.models.toFactureModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Databases
import javax.inject.Inject

class VenteDataSource @Inject constructor(
   private val database:Databases,
   private val companyDataStore: CompanyDataStore
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
        val company=companyDataStore.readCompanyData()
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
                "company" to company
            )
        )
        products.forEach { it
            RapportStoreDataSource(companyDataStore = companyDataStore, database = database)
                .createRapport(product =it, quantity = quantity, type = "OUT")
        }
        return true
    }
    // getAll factures
    suspend fun getFactures():List<FactureModel>{
        val company=companyDataStore.readCompanyData()
        val res=database.listDocuments(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "",
            queries = listOf(
                Query.equal("company",company!!),
                Query.orderDesc("\$createdAt")
            )
        )
        val factures=res.documents.map {
            it.toFactureModel()
        }
        return factures
    }
}