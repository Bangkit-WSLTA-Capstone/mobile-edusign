package com.nekkiichi.edusign.ui.screens.home.camera.no_permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
fun NoPermission_CameraScreen(requestPermission: () -> Unit = {}) {
    NoPermission_CameraContent(requestPermission = requestPermission)
}

@Composable
private fun NoPermission_CameraContent(requestPermission: () -> Unit) {
    Scaffold {
        Column(
            Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Access Camera to get camera of course, so we can capture your sign :)", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(16.dp))
            PrimaryButton(onCLick = requestPermission) {
                Icon(imageVector = Icons.Rounded.Camera, contentDescription = "Allow Camera")
                Spacer(modifier = Modifier.size(width = 4.dp, height = 0.dp))
                Text(text = "ALLOW CAMERA")
            }
        }
    }
}

@Preview
@Composable
private fun NoPermission_CameraScreenPreview(dark: Boolean = false) {
    EduSignTheme(darkTheme = dark) {
        NoPermission_CameraScreen()
    }
}

@Preview
@Composable
private fun NoPermission_CameraScreenPreviewDark() {
    NoPermission_CameraScreenPreview(true)
}