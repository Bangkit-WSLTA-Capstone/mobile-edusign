package com.nekkiichi.edusign.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.edusign.data.EdusignRepository
import com.nekkiichi.edusign.data.remote.response.TranslationResponse
import com.nekkiichi.edusign.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val repository: EdusignRepository
): ViewModel() {
    var translateResult by mutableStateOf<Status<TranslationResponse>?>(null)

    fun translateVideo(videoFile: File) {
        viewModelScope.launch {
            repository.translateVideo(videoFile).collect {
                translateResult = it
            }
        }
    }
}