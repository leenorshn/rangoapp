package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.models.toCompanyModel
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import javax.inject.Inject

class CompanyDataSource @Inject constructor(
    private val client: Client
) {

    suspend fun getCompany():List<CompanyModel>{
        val databases = Databases(client)

        try {
            val documents = databases.listDocuments(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                queries = listOf(
                   // Query.equal("title", "Hamlet")
                )
            )
            return documents.documents.map {
                it.toCompanyModel()
            }
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
            return emptyList()
        }
    }

    suspend fun createCompany(
        name:String,
        address:String,
        phone:String,
        description:String,
        type:String,
        rccm:String,
        idNat:String,
        idCommerce:String,
        logo:String,
        email:String,
    ){
        val databases = Databases(client)

        try {
            val document = databases.createDocument(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                documentId = ID.unique(),
                data = mapOf(
                    "name" to name,
                    "address" to address,
                    "phone" to phone,
                    "description" to description,
                    "type" to type,
                    "rccm" to rccm,
                    "idNat" to idNat,
                    "idCommerce" to idCommerce,
                    "logo" to logo,
                    "email" to email,
                ),
            )
            print(document)
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
        }
    }
}