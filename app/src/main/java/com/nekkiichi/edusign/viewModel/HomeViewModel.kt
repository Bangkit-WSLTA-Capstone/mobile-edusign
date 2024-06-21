package com.nekkiichi.edusign.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File

class HomeViewModel : ViewModel() {
    var videoFile: File? by mutableStateOf(null)
    fun uploadVideo() {

    }
    fun clearVideo() {
        if(videoFile != null ) {
            videoFile?.delete()
        }
        videoFile = null
    }
}