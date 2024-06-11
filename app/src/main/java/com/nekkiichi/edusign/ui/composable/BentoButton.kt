package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun BentoButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(16.dp),
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    Surface(
        onClick = onClick,
        Modifier.then(modifier),
        color = color,
        shape = shape,
    ) {
        Row {
            content()
        }
    }
}

@Preview
@Composable
private fun BentoButtonPreview() {
    EduSignTheme {
        Surface {
            Column {
                BentoButton(onClick = {}) {
                    Text(text = "Hello")
                }
            }
        }
    }
}