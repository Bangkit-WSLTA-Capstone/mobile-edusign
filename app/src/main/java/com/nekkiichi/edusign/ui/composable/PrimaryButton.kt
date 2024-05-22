package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.AppTypography
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
fun PrimaryButton(
    onCLick: () -> Unit, modifier: Modifier = Modifier, content: @Composable() (RowScope.() -> Unit)
) {
    Button(onClick = onCLick, modifier = modifier, shape = RoundedCornerShape(4.dp)) {
        ProvideTextStyle(value = AppTypography.titleSmall) {
            content()
        }
    }
}


@Preview
@Composable
private fun ButtonPreview() {
    EduSignTheme(dynamicColor = false) {
        Surface() {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Primary Button")
                }
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings icon")
                    Text(text = "with icon")
                }
            }
        }
    }
}