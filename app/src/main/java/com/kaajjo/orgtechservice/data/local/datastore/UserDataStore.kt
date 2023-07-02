package com.kaajjo.orgtechservice.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.createDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_datastore")

class UserDataStore @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.createDataStore

    private val userApiKeyKey = stringPreferencesKey("user_api_key")

    suspend fun setUserApiKey(key: String) {
        dataStore.edit { it[userApiKeyKey] = key }
    }

    val userApiKey = dataStore.data.map { it[userApiKeyKey] ?: "" }
}