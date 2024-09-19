package com.avenir.rangoapp.data.datasource


import android.util.Log
import com.avenir.rangoapp.core.BaseResponse
import com.avenir.rangoapp.data.models.UserModel
import com.avenir.rangoapp.data.models.toUserModel
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.models.Session
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val account: Account,
    private val database: Databases,
    private val companyDataStore: CompanyDataStore,
) {
    private val userDatabase = UserDataSource(database,account,companyDataStore)
    private val  companyDataSource=CompanyDataSource(database,account,companyDataStore)

    suspend fun onLogin(
        email: String,
        password: String,
    ): Flow<BaseResponse<Session>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val session = account.createEmailPasswordSession(
                    email= email,
                    password=password,
                )


               // companyDataStore.saveCompany(name =)
                emit(BaseResponse.Success(session))
            } catch (ex: Exception) {
                emit(BaseResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun onRegister(
        username: String,
        password: String,
    ): Flow<BaseResponse<User<Map<String, Any>>>> {


        return flow {
            emit(BaseResponse.Loading)
            try {

                //create user
                val acc = account.create(
                    userId = ID.unique(),
                    email= username,
                    password=password,
                )
                //login
                account.createEmailPasswordSession(username,password)
                //create empty company
             val company=   companyDataSource.createCompany(
                    name = username.split("@")[0],
                    phone = "",
                    address = ""
                )
                companyDataStore.saveCompany(name =company.id)
                // create user in db
                userDatabase.createUser(
                    uid = acc.id,
                    name= username.split("@")[0],
                    email = username,
                    phone="",
                    role="Admin",

                )
               // account.createEmailPasswordSession(username,password)
                emit(BaseResponse.Success(acc))
            } catch (ex: Exception) {
                emit(BaseResponse.Error(ex.message.toString()))
            }
        }
    }

    suspend fun onLogout() :Boolean{
       val  response=account.deleteSession("current")
       return true
    }

    suspend fun isUserLoggedIn(): Flow<BaseResponse<Boolean>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val session = account.getSession("current")
                Log.d("UserDataSource", "isUserLoggedIn: ${session.userId}")
                emit(BaseResponse.Success(true))
            } catch (e: Exception) {
                Log.e("UserDataSource", "isUserLoggedIn: ${e.message}")
                emit(BaseResponse.Error("Error ${e.message}"))
            }
        }
    }
    suspend fun getCurrentUser(): Flow<BaseResponse<UserModel>> {
        return flow {
            emit(BaseResponse.Loading)
            try {
                val session = account.getSession("current")
              val res=  database.listDocuments(
                  databaseId = "667940d2003bfd8657a8",
                  collectionId = "667940ed002fa6cc721f",
                    listOf(Query.equal("uid",session.userId))
                )
              val users=  res.documents.map {
                    it.toUserModel()
                }
                Log.d("UserDataSource",users[0].toString())
                emit(BaseResponse.Success(users[0]))
            } catch (e: Exception) {
                Log.e("UserDataSource", "isUserLoggedIn: ${e.message}")
                emit(BaseResponse.Error("Error ${e.message}"))
            }
        }
    }

    suspend fun getUser(): UserModel {
                val session = account.getSession("current")
                val res=  database.listDocuments(
                    databaseId = "667940d2003bfd8657a8",
                    collectionId = "667940ed002fa6cc721f",
                    listOf(Query.equal("uid",session.userId))
                )
                val users=  res.documents.map {
                    it.toUserModel()
                }
                Log.d("UserDataSource",users[0].toString())

            return users[0]
        }

}
