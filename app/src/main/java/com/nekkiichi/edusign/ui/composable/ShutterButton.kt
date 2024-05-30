package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun ShutterButton(onClick: () -> Unit,modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        Modifier
            .size(50.dp)
            .then(modifier),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(Icons.Filled.Camera, contentDescription = "Shutter")
    }
}


@Preview
@Composable
fun ShutterButtonPreview(dark: Boolean = false) {
    EduSignTheme(darkTheme = dark) {
        Surface {
            ShutterButton(onClick = { /*TODO*/ })
        }
    }
}