package com.nekkiichi.edusign.ui.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowRight
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
fun DashboardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    label: String,
    description: String? = null
) {
    Card(onClick = onClick) {
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
                    color = MaterialTheme.colorScheme.secondary
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DashboardButtonPreview() {
    EduSignTheme {
        Surface {
            Column {

            DashboardButton(
                onClick = {},
                icon = { Icon(Icons.Rounded.CameraAlt, contentDescription = null) },
                label = "Use using camera",
                description = "Video translate"
            )
                DashboardButton(onClick = {},label = "Open Link", description = "Optional of course")
            }
        }
    }
}