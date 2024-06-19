package com.nekkiichi.edusign.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.data.entities.TranslateHistory
import com.nekkiichi.edusign.data.local.AuthManager
import com.nekkiichi.edusign.data.remote.response.RegisterResponse
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(private val repository: EdusignRepository) : ViewModel() {
    var logoutStatus by mutableStateOf<Status<String>?>(null)
    //    init {
//        fetchAccount()
//    }
//init {
//    viewModelScope.launch {
//        authManager.init()
//    }
//}
    fun logout() {
        Log.d("test", "logout")
        viewModelScope.launch {
            repository.logout().collect {
                logoutStatus = it
            }
        }
    }
}