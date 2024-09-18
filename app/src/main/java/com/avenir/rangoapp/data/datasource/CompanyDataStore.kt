package com.avenir.rangoapp.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompanyDataStore @Inject constructor(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val COMPANY = stringPreferencesKey("store_Data")

    }
    val getCompany: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[COMPANY] ?: ""
        }

    suspend fun readCompanyData(): String? {
        return getCompany.first() // Gets the first emitted value
    }
    suspend fun saveCompany(name: String) {
        context.dataStore.edit { preferences ->
            preferences[COMPANY] = name
        }
    }
}