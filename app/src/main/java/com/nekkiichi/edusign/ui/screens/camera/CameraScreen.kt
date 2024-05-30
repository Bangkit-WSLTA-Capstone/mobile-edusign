package com.nekkiichi.edusign.ui.screens.camera

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nekkiichi.edusign.ui.screens.camera.content.Content_CameraScreen
import com.nekkiichi.edusign.ui.screens.camera.no_permission.NoPermission_CameraScreen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen() {
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    CameraContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )
}


@Composable
private fun CameraContent(hasPermission: Boolean, onRequestPermission: () -> Unit) {
    if (hasPermission) {
        Content_CameraScreen()
    } else {
        NoPermission_CameraScreen(requestPermission = onRequestPermission)
    }
}