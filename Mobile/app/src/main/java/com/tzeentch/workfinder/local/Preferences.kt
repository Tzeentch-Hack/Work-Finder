package com.tzeentch.workfinder.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.themePrefDataStore by preferencesDataStore(PreferenceManager.PREFS_NAME)

class PreferenceManagerImpl(context: Context) : PreferenceManager {

    private val dataStore = context.themePrefDataStore


    override suspend fun setAuthData(name: String, password: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_NAME] = name
            preferences[KEY_AUTH_PASSWORD] = password
        }
    }

    override suspend fun getAuthData(): Pair<String, String> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            Pair(preference[KEY_AUTH_NAME] ?: "", preference[KEY_AUTH_PASSWORD] ?: "")
        }.first()
    }


    companion object {
        val KEY_AUTH_NAME = stringPreferencesKey(PreferenceManager.PREFS_AUTH_NAME)
        val KEY_AUTH_PASSWORD = stringPreferencesKey(PreferenceManager.PREFS_AUTH_PASSWORD)
    }
}


interface PreferenceManager {

    companion object {
        const val PREFS_NAME = "com.tzeentch.workfinder"
        const val PREFS_AUTH_NAME = "PREFS_AUTH_NAME"
        const val PREFS_AUTH_PASSWORD = "PREFS_AUTH_PASSWORD"
    }

    suspend fun setAuthData(name: String, password: String)
    suspend fun getAuthData(): Pair<String, String>
}
