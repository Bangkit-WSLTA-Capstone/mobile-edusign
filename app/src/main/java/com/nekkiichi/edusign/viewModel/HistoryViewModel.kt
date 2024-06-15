package com.nekkiichi.edusign.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.data.entities.TranslateHistory
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: EdusignRepository) : ViewModel() {
    var historyState = mutableStateOf<Status<List<TranslateHistory>>>(Status.Loading)

    init {
        fetchHistories()
    }

    private fun fetchHistories(){
        viewModelScope.launch {
            repository.getTranslateHistories().collect { value ->
                historyState.value = value
            }
        }
    }

}