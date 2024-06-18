package com.nekkiichi.edusign.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
fun ModalTranslateContent(modifier: Modifier = Modifier, text: String) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Translated Text: ", style = MaterialTheme.typography.titleMedium)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Markdown(content = text, modifier = Modifier)
        }

    }
}

@Composable
fun ModalTranslateError(modifier: Modifier = Modifier, text: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier)) {
        Text(text = "Failed to retrieve result:", style = MaterialTheme.typography.titleMedium)
        Text(text = text)
    }
}

@Composable
fun ModalTranslateLoading(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(text = "Retrieving result...")
    }
}

@Preview
@Composable
private fun TranslateContentPreview() {
    EduSignTheme {
        Surface {
            ModalTranslateContent(text = "Aloha!!!")
        }
    }
}

@Preview
@Composable
private fun TranslateLoadingPreview() {
    EduSignTheme {
        Surface {
            ModalTranslateLoading()
        }
    }
}

@Preview
@Composable
private fun TranslateErrorPreview() {
    EduSignTheme {
        Surface {
            ModalTranslateError(text = "user not found")
        }
    }
}