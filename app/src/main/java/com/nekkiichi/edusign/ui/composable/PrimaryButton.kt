package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.Constant.defaultShape
import com.nekkiichi.edusign.ui.theme.AppTypography
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.ui.theme.plusJakartaSansFamily

val ButtonShape = RoundedCornerShape(16.dp)

@Composable
fun PrimaryButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: RoundedCornerShape = defaultShape,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onCLick,
        modifier = modifier,
        shape = shape,
        enabled = enabled,
        colors = colors
    ) {
        ProvideTextStyle(value = AppTypography.labelLarge) {
            content()
        }
    }
}

@Composable
fun SecondaryButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onCLick,
        modifier = modifier,
        shape = ButtonShape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = Color.White
        )
    ) {
        ProvideTextStyle(value = AppTypography.labelLarge) {
            content()
        }
    }
}

@Composable
fun OutlineButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable (RowScope.() -> Unit)
) {
    OutlinedButton(
        onClick = onCLick,
        modifier = modifier,
        shape = ButtonShape,
        enabled = enabled,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        ProvideTextStyle(value = AppTypography.labelLarge) {
            content()
        }
    }
}

@Composable
fun TextButton(
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable (RowScope.() -> Unit)
) {
    androidx.compose.material3.TextButton(
        onClick = onCLick,
        modifier = modifier,
        enabled = enabled,
        shape = ButtonShape
    ) {
        ProvideTextStyle(value = AppTypography.labelLarge.copy(fontFamily = plusJakartaSansFamily)) {
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
                SecondaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Secondary Button")
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
                SecondaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Secondary Button")
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