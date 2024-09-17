package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.RapportStoreModel
import com.avenir.rangoapp.data.models.toRapportStoreModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject

class RapportStoreDataSource @Inject constructor(
    private val  database:Databases,
    private  val account: Account
) {
    // create report
    suspend fun createRapport(
        productId:String,
        productName:String,
        type:String,
    ):Boolean{
        val session= account.getSession("current")
        var result= database.createDocument(
            databaseId = "",
            collectionId = "",
            documentId = ID.unique(),
            data = mapOf(
                "productId" to productId,
                "productName" to productName,
                "type" to type,
                "company" to session.userId
            )
        )
        return true
    }
    // getRapportStore()
    suspend fun getRapportStore():List<RapportStoreModel>{
        val session=account.getSession("current")
        val result= database.listDocuments(
            databaseId = "",
            collectionId = "",
            queries = listOf(
                Query.equal("company",session.userId),
                Query.orderDesc("createdAt")
            )
        )

        val products=result.documents.map {
            it.toRapportStoreModel()
        }

        return products
    }
}