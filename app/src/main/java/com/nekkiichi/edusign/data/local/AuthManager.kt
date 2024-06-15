package com.nekkiichi.edusign.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
    private val isWelcomeVisitedKey = booleanPreferencesKey("isWelcomeVisited")

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent.asSharedFlow()

    private val _loginEvent = MutableSharedFlow<Unit>()
    val loginEvent = _loginEvent.asSharedFlow()
    private val _welcomeEvent = MutableSharedFlow<Unit>()
    val welcomeEvent = _welcomeEvent.asSharedFlow()

    init {

    }

    suspend fun init() {
        val welcomeVisited = context.authDataStore.data.map { it[isWelcomeVisitedKey] ?: false }.first()
        if(welcomeVisited == false) {
            context.authDataStore.edit {
                it[isWelcomeVisitedKey] = true
            }
            _welcomeEvent.emit(Unit)
            return
        }
        Log.d(TAG,"Check token validity")
        if (getToken().isNotEmpty()) {
            Log.d(TAG, "Emit loginEvent")
            _loginEvent.emit(Unit)
        }else{
            _logoutEvent.emit(Unit)
        }
    }

    suspend fun logout() {
        saveToken("")
        _logoutEvent.emit(Unit)
    }

    suspend fun saveToken(token: String) {
        Log.d(TAG, "Save token, logged in")
        context.authDataStore.edit {
            it[tokenKey] = token
        }
    }

    suspend fun getToken(): String {
        Log.d(TAG, "Retrieve token")
        return context.authDataStore.data.map { value: Preferences -> value[tokenKey] ?: "" }
            .first()
    }

    companion object {
        private const val TAG = "AuthManager"
    }
}