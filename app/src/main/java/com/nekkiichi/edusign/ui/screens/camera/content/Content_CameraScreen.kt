package com.nekkiichi.edusign.ui.screens.camera.content

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlipCameraAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nekkiichi.edusign.ui.composable.ShutterButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.viewModel.CameraViewModel


@Composable
fun Content_CameraScreen(modifier: Modifier = Modifier) {
    val viewModel: CameraViewModel = viewModel()

    val isRecording by viewModel.isRecording.collectAsState()

    var lensFacing by remember {
        mutableIntStateOf(CameraSelector.LENS_FACING_BACK)
    }
    var cameraSelector by remember {
        mutableStateOf(CameraSelector.Builder().requireLensFacing(lensFacing).build())
    }

    fun changeLensFacing() {
        lensFacing =
            if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                CameraSelector.LENS_FACING_FRONT
            } else {
                CameraSelector.LENS_FACING_BACK
            }
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .then(modifier)
        ) {
            CameraPreview(
                Modifier.align(Alignment.Center), cameraSelector = cameraSelector
            )
            // Bottom controller
            BottomCamControl(
                isRecording = isRecording,
                onRecordCLick = {
                    viewModel.toggleRecording()
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
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context).apply {
            this.scaleType = scaleType
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    LaunchedEffect(key1 = cameraSelector) {
        val preview = androidx.camera.core.Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        try {
            cameraProviderFuture.get().unbindAll()
            cameraProviderFuture.get().bindToLifecycle(lifecycleOwner, cameraSelector, preview)

        } catch (e: Exception) {
            Log.e("CameraPreview", "Binding failed:", e)
        }
    }

    AndroidView(modifier = modifier, factory = { previewView })
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
        }, Modifier.rotate(rotation)) {
            Icon(
                Icons.Rounded.FlipCameraAndroid,
                contentDescription = "",
                Modifier
                    .size(32.dp)
                    .graphicsLayer {

                    },
                tint = Color.White
            )
        }
    }
}


@Preview
@Composable
private fun CameraScreenPreview(modifier: Modifier = Modifier, dark: Boolean = false) {
    EduSignTheme {
        Surface {
            Content_CameraScreen()
        }
    }
}