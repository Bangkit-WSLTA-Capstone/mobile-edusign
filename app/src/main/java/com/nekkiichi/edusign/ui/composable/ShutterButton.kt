package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun ShutterButton(onClick: () -> Unit, isPlayed: Boolean = false, modifier: Modifier = Modifier) {

    Button(
        onClick = onClick,
        Modifier
            .size(72.dp)
            .then(modifier),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color(0xFFffc524)
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (isPlayed) {
            Icon(Icons.Filled.Stop, contentDescription = "Shutter")
        } else {
            Icon(Icons.Filled.Videocam, contentDescription = "Shutter")
        }
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