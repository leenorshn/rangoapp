package com.avenir.rangoapp.data.datasource

import android.util.Log
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.models.toUserModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val database: Databases,
) {

    suspend fun createUser(
        uid: String,
        name: String,
        phone: String,
        companyId: String,
        role: String,
    ) {

        try {
            val document = database.createDocument(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "667940ed002fa6cc721f",
                documentId = ID.unique(),
                data = mapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to "$phone@rango.com",
                    "phone" to phone,
                    "role" to role,
                    "isBlocked" to false,
                    "company" to companyId
                )
            )
            Log.d("UserDataSource", "createUser: ${document.id}")
        } catch (ex: AppwriteException) {
            print(ex.message)
            Log.e("UserDataSource", "createUser: ${ex.message}")
        }
    }

    suspend fun getUsers(companyId: String) :List<UserModel>{
        try {
            val documents = database.listDocuments(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                queries = listOf(
                    Query.equal("company", companyId),
                    Query.notEqual("isBlocked", true),
                ),
            )
            return documents.documents.map {
                it.toUserModel()
            }
        } catch (ex: AppwriteException) {
            print(ex.message)
            return emptyList()
        }
    }
}