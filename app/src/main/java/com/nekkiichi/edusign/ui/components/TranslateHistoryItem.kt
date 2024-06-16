package com.nekkiichi.edusign.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

@Composable
fun TranslateHistoryItem(modifier: Modifier = Modifier, title: String, date: Date) {
    val stringDate = SimpleDateFormat("HH:mm, dd MMM yyyy", java.util.Locale.ENGLISH).format(date)
    Card(
        modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(text = stringDate, style = MaterialTheme.typography.labelMedium)
            Text(text = title, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }

    }
}


@Preview
@Composable
private fun TranslateHistoryItemPreview() {
    EduSignTheme {
        Surface {
            TranslateHistoryItem(Modifier.fillMaxWidth(),title = "Dummy result", date = Date.from(Instant.now()))
        }
    }
}