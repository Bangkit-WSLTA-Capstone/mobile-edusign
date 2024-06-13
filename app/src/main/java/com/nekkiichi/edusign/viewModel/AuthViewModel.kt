package com.nekkiichi.edusign.viewModel

import androidx.lifecycle.ViewModel
import com.nekkiichi.edusign.data.local.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(authManager: AuthManager) : ViewModel() {
    val logoutEvent = authManager.logoutEvent
}