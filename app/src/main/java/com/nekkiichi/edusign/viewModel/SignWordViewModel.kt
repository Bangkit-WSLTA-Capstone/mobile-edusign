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
class SignWordViewModel @Inject constructor(val repository: EdusignRepository): ViewModel() {
    val currentSignWord = MutableStateFlow<Status<String>?>(null)

    fun getWord(choosenWord: String) {
        viewModelScope.launch {
            repository.getDictionary(choosenWord).collect {
                currentSignWord.value = it
            }
        }
    }
    fun clear() {
        currentSignWord.value = null
    }
}