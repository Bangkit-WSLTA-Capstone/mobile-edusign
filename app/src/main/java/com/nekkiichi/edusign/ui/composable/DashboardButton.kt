package com.nekkiichi.edusign.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowRight
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.screens.home.DashboardScreen
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
fun DashboardButton(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    label: String,
    description: String? = null
) {
    Card(onClick = { /*TODO*/ }) {
        Row(
            Modifier
                .padding(8.dp)
                .heightIn(min = 64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Surface(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(64.dp),
                    color = MaterialTheme.colorScheme.tertiary
                ) {
                    Box {
                        Column(
                            Modifier
                                .align(Alignment.Center)
                                .size(24.dp)
                        ) {
                            icon()
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (description != null) {
                    Text(text = description, style = MaterialTheme.typography.bodySmall)
                }
            }
            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowRight, contentDescription = null)
        }

    }
}

@Preview
@Composable
private fun DashboardButtonPreview() {
    EduSignTheme {
        Surface {
            Column {

            DashboardButton(
                icon = { Icon(Icons.Rounded.CameraAlt, contentDescription = null) },
                label = "Use using camera",
                description = "Video translate"
            )
                DashboardButton(label = "Open Link", description = "Optional of course")
            }
        }
    }
}