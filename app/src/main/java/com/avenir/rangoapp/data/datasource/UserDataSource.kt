package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.models.toUserModel
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val client: Client
) {

    suspend fun createUser(
        name: String,
        email: String,
        companyId: String,
        role: String,
    ) {
        val databases = Databases(client)

        try {
            val document = databases.createDocument(
                databaseId = "667940d2003bfd8657a8",
                collectionId = "6679421c0013ffb9cad4",
                documentId = ID.unique(),
                data = mapOf(

                    "name" to name,
                    "email" to email,
                    "role" to role,
                    "isBlocked" to false,
                    "company" to companyId
                )
            )
        } catch (ex: AppwriteException) {
            print(ex.message)
        }
    }

    suspend fun getUsers(companyId: String) :List<UserModel>{
        try {
            val databases = Databases(client)

            val documents = databases.listDocuments(
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