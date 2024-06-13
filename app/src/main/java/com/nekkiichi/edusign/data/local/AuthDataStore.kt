package com.nekkiichi.edusign.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AuthDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.authDataStore by preferencesDataStore(name = "auth")

    private val tokenKey = stringPreferencesKey("token")

    suspend fun saveToken(token: String) {
         context.authDataStore.edit {
            it[tokenKey] = token
        }
    }

    suspend fun getToken(): String {
        return context.authDataStore.data.map { value: Preferences -> value[tokenKey] ?: "" }.first()
    }
}