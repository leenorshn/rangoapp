package com.avenir.rangoapp.data.datasource

import com.avenir.rangoapp.data.models.ProductModel
import com.avenir.rangoapp.data.models.toProductModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject

class ProductDataSource@Inject constructor(
    private val account: Account,
    private val database: Databases,
)  {

    // create product
    suspend fun createProduct(name:String,mark:String,priceVente:Double,priceAchat:Double,stock:Int):Boolean{
        val  session=account.getSession("current");
        val productId=ID.unique()
       var res= database.createDocument(
           "667940d2003bfd8657a8","66e6d4dc002117e2b153", documentId = productId,data = mapOf(
            "company" to session.userId,
               "mark" to mark,
            "name" to name,
            "priceVente" to priceVente,
            "priceAchat" to priceAchat,
            "stock" to stock
        ))
        RapportStoreDataSource(account = account, database = database)
            .createRapport(product =productId, quantity = stock, type = "IN")
        return true
    }
    // update product
    suspend fun updateProduct(product:ProductModel):Boolean{
        var res= database.updateDocument(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e6d4dc002117e2b153",
            documentId = product.id,
            data = mapOf(
                "company" to product.company,
                "name" to product.name,
                "priceVente" to product.priceVente,
                "priceAchat" to product.priceAchat,
                "stock" to product.stock
            )
        )
        return true
    }
    // delete product
    suspend fun deleteProduct(id:String):Boolean{
        var res= database.deleteDocument(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e6d4dc002117e2b153",
            documentId = id
        )
        return true
    }
    // get all products
    suspend fun getAllProducts():List<ProductModel>{
        val  session=account.getSession("current");
        val res= database.listDocuments(
            databaseId = "667940d2003bfd8657a8",
            collectionId = "66e6d4dc002117e2b153",
            queries = listOf(
                Query.equal("company",session.userId)
            )
        )
        val products=res.documents.map {
            it.toProductModel()
        }
        return products
    }
    // get a product
    suspend fun getProduct(id:String):ProductModel{
        val res=database.getDocument("667940d2003bfd8657a8", collectionId = "66e6d4dc002117e2b153", documentId = id)
        return res.toProductModel()
    }
}