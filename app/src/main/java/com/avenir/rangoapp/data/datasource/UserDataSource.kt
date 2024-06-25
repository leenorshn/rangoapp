package com.avenir.rangoapp.data.datasource



import io.appwrite.ID
import io.appwrite.models.Session
import io.appwrite.models.User
import io.appwrite.services.Account
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val account: Account,
) {
    suspend fun onLogin(
        email: String,
        password: String,
    ): Session {
        return account.createEmailPasswordSession(
            email,
            password,
        )
    }

    suspend fun onRegister(
        email: String,
        password: String,
    ): User<Map<String, Any>> {

        return account.create(
            userId = ID.unique(),
            email,
            password,
        )
    }

    suspend fun onLogout() {
        account.deleteSession("current")
    }

    suspend fun isUserLoggedIn(): Boolean {
        // Remplacez ceci par votre logique de vérification de session
        return try {
            account.getSession("current")
            true
        } catch (e: Exception) {
            false
        }
    }
}
