package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.AppTypography
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.ui.theme.geistMonoFamily

val ButtonShape = RoundedCornerShape(4.dp)

@Composable
fun PrimaryButton(
    onCLick: () -> Unit, modifier: Modifier = Modifier, content: @Composable() (RowScope.() -> Unit)
) {
    Button(onClick = onCLick, modifier = modifier, shape = ButtonShape) {
        ProvideTextStyle(value = AppTypography.labelLarge.copy(fontFamily = geistMonoFamily)) {
            content()
        }
    }
}

@Composable
fun OutlineButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit)
) {
    OutlinedButton(onClick = onCLick, modifier = modifier, shape = ButtonShape) {
        ProvideTextStyle(value = AppTypography.labelLarge.copy(fontFamily = geistMonoFamily)) {
            content()
        }
    }
}

@Composable
fun TextButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit)
) {
    androidx.compose.material3.TextButton(
        onClick = onCLick,
        modifier = modifier,
        shape = ButtonShape
    ) {
        ProvideTextStyle(value = AppTypography.labelLarge.copy(fontFamily = geistMonoFamily)) {
            content()
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    EduSignTheme(dynamicColor = false, darkTheme = false) {
        Surface() {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Primary Button")
                }
                OutlineButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Outline Button")
                }
                TextButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Textos Button")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ButtonPreviewDark() {
    EduSignTheme(dynamicColor = false, darkTheme = true) {
        Surface() {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Primary Button")
                }
                OutlineButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Outline Button")
                }
                TextButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Textos Button")
                }
            }
        }
    }
}