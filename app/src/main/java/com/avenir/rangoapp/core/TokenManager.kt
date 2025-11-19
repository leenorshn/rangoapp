package com.avenir.rangoapp.core

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("rango_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString(Constants.JWT_TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.JWT_TOKEN_KEY, null)
    }

    fun clearToken() {
        prefs.edit().remove(Constants.JWT_TOKEN_KEY).apply()
    }

    fun hasToken(): Boolean {
        return getToken() != null
    }
}

