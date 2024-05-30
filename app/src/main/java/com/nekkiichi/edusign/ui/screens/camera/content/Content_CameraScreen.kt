package com.nekkiichi.edusign.ui.screens.camera.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun Content_CameraScreen(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        // Bottom controller
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "PREV")
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Camera, contentDescription = "Capture")
            }
            Text(text = "SWTC")
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