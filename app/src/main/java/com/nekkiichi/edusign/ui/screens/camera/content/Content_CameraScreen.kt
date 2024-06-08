package com.nekkiichi.edusign.ui.screens.camera.content

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.FileExt
import com.nekkiichi.edusign.utils.toFormattedTime


@Composable
fun Content_CameraScreen(modifier: Modifier = Modifier) {
    val handler = remember {
        Handler(Looper.getMainLooper())
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val camController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }
    var recording: Recording? by remember {
        mutableStateOf(null)
    }

    var startTime by remember {
        mutableStateOf(SystemClock.elapsedRealtime())
    }
    var currentTime by remember {
        mutableStateOf("")
    }

    val updateTimer = object : Runnable {
        override fun run() {
            val _currentTime = SystemClock.elapsedRealtime() - startTime
            val timeStr = _currentTime.toFormattedTime()
            currentTime = timeStr
            handler.postDelayed(this, 1000)
        }
    }

    fun changeLensFacing() {
        camController.cameraSelector =
            if (camController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
    }

    fun recordVideo() {
        if (recording != null) {
            recording?.stop()
            recording = null
            handler.removeCallbacks(updateTimer)
            return
        }

        // start recording video
        val outputFile = FileExt.createTempVideoFile(context)
        recording = camController.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.AUDIO_DISABLED,
            ContextCompat.getMainExecutor(context.applicationContext)
        ) {
            when (it) {
                is VideoRecordEvent.Finalize -> {
                    if (it.hasError()) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Recording successfully", Toast.LENGTH_SHORT).show()
                        Log.d("CameraScreen", "path: ${outputFile.path}")
                    }
                }

                is VideoRecordEvent.Start -> {
                    startTime = SystemClock.elapsedRealtime()
                    handler.post(updateTimer)
                }

                else -> {}
            }
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .then(modifier)
        ) {
            CameraPreview(
                Modifier.align(Alignment.Center),
                controller = camController
            )
            RecordStatus(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp),
                visible = recording != null,
                value = currentTime
            )
            // Bottom controller
            BottomCamControl(
                isRecording = recording != null,
                onRecordCLick = {
                    recordVideo()
                },

                onCameraFlipClick = { changeLensFacing() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


@Preview
@Composable
private fun CameraScreenPreview(modifier: Modifier = Modifier, dark: Boolean = false) {
    EduSignTheme {
        Surface {
            Scaffold {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .then(modifier)
                ) {
//                    CameraPreview(
//                        Modifier.align(Alignment.Center),
//                        controller = LifecycleCameraController(LocalContext.current)
//                    )
                    RecordStatus(
                        Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 8.dp),
                        value = "00.00:00",
                        visible = true
                    )
                    // Bottom controller
                    BottomCamControl(
                        isRecording = false,
                        onRecordCLick = {
                        },

                        onCameraFlipClick = { },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
