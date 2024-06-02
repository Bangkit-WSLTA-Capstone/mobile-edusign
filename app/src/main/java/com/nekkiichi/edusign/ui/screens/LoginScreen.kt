package com.nekkiichi.edusign.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
private fun Main() {
    Text(text = "Welcome")
}

@Preview
@Composable
private fun GuideItem3Preview() {
    EduSignTheme(dynamicColor = false) {
        Surface {
            Main()
        }
    }
}