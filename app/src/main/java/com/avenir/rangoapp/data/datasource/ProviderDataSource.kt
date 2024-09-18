package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.ProviderModel
import com.avenir.rangoapp.data.models.toProviderModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Databases
import javax.inject.Inject

class ProviderDataSource @Inject constructor(
    val  database:Databases,
    val   companyDataStore: CompanyDataStore,
) {
    suspend fun createProvider(
        name:String,
        phone:String,
        address:String
    ):Boolean{
      val company = companyDataStore.readCompanyData()
        val res=database.createDocument(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e97382002a28fea073",
            documentId = ID.unique(),
            data = mapOf(
                "name" to name,
                "phone" to phone,
                "address" to address,
                "company" to company
            )
        )
        return true
    }

    suspend fun getProviders():List<ProviderModel>{
        val company = companyDataStore.readCompanyData()
        val result = database.listDocuments(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e97382002a28fea073",
            queries = listOf(
                Query.equal("company",company!!)
            )
        )

        val providers=result.documents.map {
            it.toProviderModel()
        }

        return  providers
    }
}