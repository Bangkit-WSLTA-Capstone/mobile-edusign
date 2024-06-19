package com.nekkiichi.edusign.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(private val repository: EdusignRepository) : ViewModel() {
    var logoutStatus = MutableStateFlow<Status<String>?>(null)

    fun logout() {
        viewModelScope.launch {
            repository.logout().collect {
                logoutStatus.value = it
            }
        }
    }
}