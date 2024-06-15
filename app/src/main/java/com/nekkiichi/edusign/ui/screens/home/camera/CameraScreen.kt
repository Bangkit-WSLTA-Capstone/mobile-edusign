package com.nekkiichi.edusign.ui.screens.home.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nekkiichi.edusign.ui.screens.home.camera.content.Content_CameraScreen
import com.nekkiichi.edusign.ui.screens.home.camera.no_permission.NoPermission_CameraScreen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController
) {
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    CameraContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        navController = navController
    )
}


@Composable
private fun CameraContent(
    modifier: Modifier = Modifier,
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    navController: NavController
) {
    if (hasPermission) {
        Content_CameraScreen(navController)
    } else {
        NoPermission_CameraScreen(requestPermission = onRequestPermission)
    }
}