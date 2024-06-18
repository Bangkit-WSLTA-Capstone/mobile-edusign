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
class MinicourseContentViewModel @Inject constructor(private val repository: EdusignRepository) :
    ViewModel() {
    var markdownString = MutableStateFlow<Status<String>>(Status.Loading)

    fun fetchMarkdown(filename: String) {
        viewModelScope.launch {
            repository.getCourse(filename).collect {
                markdownString.value = it
            }
        }
    }

}