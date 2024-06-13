package com.nekkiichi.edusign.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AuthManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.authDataStore by preferencesDataStore(name = "auth")

    private val tokenKey = stringPreferencesKey("token")

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    suspend fun onLogout() {
        _logoutEvent.emit(Unit)
    }

    suspend fun saveToken(token: String) {
         context.authDataStore.edit {
            it[tokenKey] = token
        }
    }

    suspend fun getToken(): String {
        return context.authDataStore.data.map { value: Preferences -> value[tokenKey] ?: "" }.first()
    }
}