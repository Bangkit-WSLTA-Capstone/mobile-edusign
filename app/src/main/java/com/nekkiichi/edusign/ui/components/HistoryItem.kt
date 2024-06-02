package com.nekkiichi.edusign.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun HistoryItem(modifier: Modifier = Modifier) {
    val markdown = """
# What's included 🚀

- Super simple setup
- Cross-platform ready
- Lightweight
""".trimIndent()

//
    Surface(Modifier, tonalElevation = 2.dp) {
        Row(
            Modifier
                .padding(8.dp)
                .heightIn(0.dp, 60.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Image")
            VerticalDivider()
            Text(text = "Welcome to new world", Modifier.weight(1f))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Edit")
            }
        }
    }
}

@Preview("HistoryItem Preview (Light Mode)")
@Composable
private fun HistoryItemPreview(modifier: Modifier = Modifier, dark: Boolean = false) {
    EduSignTheme(darkTheme = dark) {
        Surface {
            Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                HistoryItem()
                HistoryItem()
                HistoryItem()
                HistoryItem()
            }
        }
    }
}
@Preview
@Composable
fun HistoryItemPreviewDart(modifier: Modifier = Modifier) {
    HistoryItemPreview(dark = true)
}
