package com.nekkiichi.edusign.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MinicourseContentViewModel @Inject constructor(private val repository: EdusignRepository) :
    ViewModel() {
    var markdownString by mutableStateOf<com.nekkiichi.edusign.utils.Status<String>?>(null)

    fun fetchMarkdown(filename: String) {
        viewModelScope.launch {
            repository.getCourse(filename).collect {
                markdownString = it
            }
        }
    }

}