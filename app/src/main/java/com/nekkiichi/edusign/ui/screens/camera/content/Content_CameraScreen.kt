package com.nekkiichi.edusign.ui.screens.camera.content

import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.nekkiichi.edusign.ui.composable.ShutterButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun Content_CameraScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var lensFacing by remember {
        mutableIntStateOf(CameraSelector.LENS_FACING_BACK)
    }
    val preview = androidx.camera.core.Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing).build()

    LaunchedEffect(key1 = lensFacing) {
        val provider = suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { camProvider ->
                camProvider.addListener({
                    continuation.resume(camProvider.get())
                }, ContextCompat.getMainExecutor(context))
            }
        }
        provider.unbindAll()
        provider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .then(modifier)
        ) {
            AndroidView(
                factory = { previewView },
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
            // Bottom controller
            BottomCamControl(
                onShutterCLick = { /*TODO*/ },
                onCameraFlipClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }
}

@Composable
private fun BottomCamControl(
    onShutterCLick: () -> Unit,
    onCameraFlipClick: () -> Unit,
    modifier: Modifier
) {

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
        ShutterButton(onClick = onShutterCLick)
        IconButton(onClick = onCameraFlipClick) {
            Icon(
                Icons.Rounded.FlipCameraAndroid,
                contentDescription = "",
                Modifier.size(32.dp),
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