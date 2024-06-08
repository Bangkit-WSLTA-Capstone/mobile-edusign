package com.nekkiichi.edusign.ui.screens.camera.content

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material.icons.rounded.FlipCameraAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.nekkiichi.edusign.ui.composable.ShutterButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
internal fun CameraPreview(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = {
            PreviewView(it).apply {
                layoutParams =
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }.also { previewView ->
                previewView.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
    )
}

@Composable
internal fun BottomCamControl(
    onRecordCLick: () -> Unit,
    onCameraFlipClick: () -> Unit,
    isRecording: Boolean = false,
    modifier: Modifier = Modifier
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
internal fun RecordStatus(
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    value: String
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        modifier,
        label = "Record Animation",
        enter = slideInVertically {
            with(density) {
                -50.dp.roundToPx()
            }
        } + fadeIn(),
        exit = slideOutVertically {
            with(density) {
                -50.dp.roundToPx()
            }
        } + fadeOut()
    ) {
        Row(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.8f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(Icons.Rounded.FiberManualRecord, contentDescription = "", tint = Color.Red)
            Text(text = "REC", color = Color.White)
            Text(text = value, color = Color.White)
        }
    }
}


@Preview
@Composable
private fun RecordStatusPreview(modifier: Modifier = Modifier) {
    EduSignTheme {
        RecordStatus(value = "00.00:00", visible = true)
    }
}

@Preview
@Composable
private fun BottomCamControlPreview(modifier: Modifier = Modifier) {
    EduSignTheme {
        BottomCamControl(
            onRecordCLick = { /*TODO*/ },
            onCameraFlipClick = { /*TODO*/ },
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun CameraPreviewPreview(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    EduSignTheme {
        CameraPreview(controller = LifecycleCameraController(context))
    }
}