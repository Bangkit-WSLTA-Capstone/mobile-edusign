package com.nekkiichi.edusign.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.data.local.AuthManager
import com.nekkiichi.edusign.data.remote.response.LoginResponse
import com.nekkiichi.edusign.data.remote.response.RegisterResponse
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val repository: EdusignRepository
) : ViewModel() {
    val logoutEvent = authManager.logoutEvent
    val loginEvent = authManager.loginEvent
    val welcomeEvent = authManager.welcomeEvent

    var loginStatus by mutableStateOf<Status<LoginResponse>?>(null)
    var registerStatus by mutableStateOf<Status<RegisterResponse>?>(null)

    init {
        viewModelScope.launch {
            authManager.init()
        }
    }

    fun login(email: String, password: String) {
        reset()
        viewModelScope.launch {
            repository.login(email, password).collect { data ->
                loginStatus = data
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        reset()
        viewModelScope.launch {
            repository.register(username, email, password).collect {
                registerStatus = it
            }
        }
    }


    fun reset() {
        loginStatus = null
        registerStatus = null
    }
}