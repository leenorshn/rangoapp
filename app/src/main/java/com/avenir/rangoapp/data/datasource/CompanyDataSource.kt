package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.avenir.rangoapp.data.models.CompanyModel
import com.avenir.rangoapp.data.models.toCompanyModel
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject

class CompanyDataSource @Inject constructor(
    private val database: Databases,
    private  val account:Account,
    private  val companyDataStore: CompanyDataStore
) {

    suspend fun getCompany(docId:String):CompanyModel{
        try {
            val document = database.getDocument(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                documentId = docId
            )
            return document.toCompanyModel()
        } catch (e: AppwriteException) {
            Log.e("Appwrite", "Error: " + e.message)
            throw e
        }
    }

    suspend fun createCompany(
        name:String,
        address:String,
        phone:String,


    ):Document<Map<String,Any>>{


        try {
            val docId=ID.unique()
            val user=account.getSession("current")
               // .wait()
            val document = database.createDocument(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                documentId = docId,
                data = mapOf(
                    "id" to docId,
                    "name" to name,
                    "address" to address,
                    "phone" to phone,
                    "description" to "",
                    "type" to "",
                    "rccm" to "",
                    "idNat" to "",
                    "idCommerce" to "",
                    "logo" to "",
                    "owner" to user.userId,
                    "email" to user.provider,
                ),
            )
            print(document)
            Log.d("Appwrite", "Document created ${document.id}")

            return document
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            throw e
        }
    }

    suspend fun updateCompany(
        name:String,
        address:String,
        phone:String,


        ):Document<Map<String,Any>>{


        try {
           // val docId=ID.unique()
            //val user=account.getSession("current")
            // .wait()

          val company=  companyDataStore.readCompanyData()


            val document = database.updateDocument(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                documentId = company!!,
                data = mapOf(
                    "id" to company,
                    "name" to name,
                    "address" to address,
                    "phone" to phone,
//                    "description" to "",
//                    "type" to "",
//                    "rccm" to "",
//                    "idNat" to "",
//                    "idCommerce" to "",
//                    "logo" to "",
//                    "owner" to ,
//                    "email" to "",
                ),
            )
            print(document)
            Log.d("Appwrite", "Document created ${document.id}")

            return document
        } catch (e: Exception) {
            Log.e("Appwrite", "Error: " + e.message)
            throw e
        }
    }
}