package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.data.models.toRapportStoreModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Databases
import javax.inject.Inject

class RapportStoreDataSource @Inject constructor(
    private val  database:Databases,
    private  val companyDataStore: CompanyDataStore
) {
    // create report
    suspend fun createRapport(
        product:String,
        quantity:Number,
        type:String,
    ):Boolean{
        val company=companyDataStore.readCompanyData()

        println(company+"***************")
        var result= database.createDocument(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e8161c0014391d854c",
            documentId = ID.unique(),
            data = mapOf(
                "product" to product,
                "quantity" to quantity,
                "type" to type,
                "company" to company
            )
        )
        return true
    }
    // getRapportStore()
    suspend fun getRapportStore():List<RapportStoreModel>{
        val company=companyDataStore.readCompanyData()
        val result= database.listDocuments(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e8161c0014391d854c",
            queries = listOf(
                Query.equal("company",company!!),
                Query.orderDesc("\$createdAt")
            )
        )

        val products=result.documents.map {
            println(it.data.toString())
            it.toRapportStoreModel()
        }

        return products
    }
}