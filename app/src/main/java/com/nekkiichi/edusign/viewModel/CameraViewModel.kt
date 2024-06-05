package com.nekkiichi.edusign.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraViewModel : ViewModel() {
    private var _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    fun toggleRecording() {
        if (_isRecording.value) {
            stop()
        } else {
            play()
        }
    }
    fun play() {
        _isRecording.value = true
    }

    fun stop() {
        _isRecording.value = false
    }
}