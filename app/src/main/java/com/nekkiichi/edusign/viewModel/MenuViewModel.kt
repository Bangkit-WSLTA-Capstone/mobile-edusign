package com.nekkiichi.edusign.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(private val repository: EdusignRepository) : ViewModel() {
    var logoutStatus = MutableStateFlow<Status<String>?>(null)
    var username = MutableStateFlow<String?>(null)
    var email = MutableStateFlow<String?>(null)

    init {
        getUser()
    }

    fun logout() {
        viewModelScope.launch {
            username.value = null
            email.value = null
            repository.logout().collect {
                logoutStatus.value = it
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            repository.getUser().collect { token ->
                if (token.value.isNotEmpty()) {
                    val decodedJWT = JWT(token.value)
                    decodedJWT.getClaim("username").asString()?.let { username.value = it }
                    decodedJWT.getClaim("email").asString()?.let { email.value = it }
                }
            }
        }
    }
}