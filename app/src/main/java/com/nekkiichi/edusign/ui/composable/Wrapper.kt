package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Wrapper(
    modifier: Modifier = Modifier,
    fillWidth: Boolean = true,
    fitContent: Boolean = false,
    content: @Composable () -> Unit
) {
        Column(
            modifier = modifier
                .then(
                    if (fitContent) Modifier else Modifier.padding(horizontal = (15 / 2).dp)
                )
                .then(
                    if (!fillWidth) Modifier else Modifier.fillMaxWidth()
                )
        ) {
            content()
        }

}

@Preview(showBackground = true)
@Composable
fun WrapperPreview() {
    Wrapper() {
        Text(text = "Wrapper content")
    }
}
