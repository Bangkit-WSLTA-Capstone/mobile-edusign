package com.nekkiichi.edusign.ui.screens.camera.content

import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.camera.view.video.AudioConfig
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlipCameraAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nekkiichi.edusign.ui.composable.ShutterButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.FileExt
import com.nekkiichi.edusign.viewModel.CameraViewModel

@Composable
fun Content_CameraScreen(modifier: Modifier = Modifier) {
    val viewModel: CameraViewModel = viewModel()
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
                    }
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

@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = {
            PreviewView(it).apply {
                layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }.also { previewView ->
                previewView.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
    )
}

@Composable
private fun BottomCamControl(
    onRecordCLick: () -> Unit,
    onCameraFlipClick: () -> Unit,
    isRecording: Boolean = false,
    modifier: Modifier
) {
    var toggleFlipPressed by remember {
        mutableStateOf(false)
    }
    val rotation by animateFloatAsState(
        targetValue = if (toggleFlipPressed) 180f else 0f,
        animationSpec = tween(500),
        label = "Rotation"
    )

    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .then(modifier),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(text = "PREV")
        ShutterButton(onClick = onRecordCLick, isRecording)
        IconButton(onClick = {
            toggleFlipPressed = !toggleFlipPressed
            onCameraFlipClick()
        }, Modifier.rotate(rotation), enabled = !isRecording) {
            Icon(
                Icons.Rounded.FlipCameraAndroid,
                contentDescription = "",
                Modifier
                    .size(32.dp)
                    .graphicsLayer {

                    },
                tint = Color.White.copy(alpha = LocalContentColor.current.alpha)
            )
        }
    }
}

@Composable
private fun RecordStatus(modifier: Modifier = Modifier) {
    Row(
        Modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "REC", color = Color.White)
        Text(text = "00.00:00", color = Color.White)
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
                    CameraPreview(
                        Modifier.align(Alignment.Center),
                        controller = LifecycleCameraController(LocalContext.current)
                    )
                    RecordStatus(
                        Modifier
                            .align(Alignment.TopCenter)
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

@Preview
@Composable
private fun RecordStatusPreview(modifier: Modifier = Modifier) {
    EduSignTheme {
        RecordStatus()
    }
}