package com.nekkiichi.edusign.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.data.remote.response.CourseItem
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MinicoursesViewModel @Inject constructor(private val repository: EdusignRepository): ViewModel() {
    val minicoursesState = MutableStateFlow<Status<List<CourseItem>>>(Status.Loading)

    init {
        fetchMinicourses()
    }

    fun fetchMinicourses() {
        viewModelScope.launch {
            repository.getCourses().collect {
                minicoursesState.value = it
            }
        }
    }
}