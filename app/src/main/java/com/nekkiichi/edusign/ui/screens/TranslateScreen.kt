package com.nekkiichi.edusign.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme

@Composable
fun TranslateScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TranslateContent() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TRANSLATE") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Rounded.History,
                            contentDescription = "Share"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = " RESET ")
                }
                LargeFloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Rounded.Camera, contentDescription = "Open Camera")
                }
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "HISTORY")
                }
            }
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(horizontal = 16.dp)) {
            Text(text = "Video Source", style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier)
            Text(text = "Translate (EN)", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(value = "FIsh", onValueChange = {})
        }
    }
}

@Composable
private fun VideoViewer(modifier: Modifier = Modifier, mediaSource: MediaSource) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val exoplayer = ExoPlayer.Builder(context).build()

    LaunchedEffect(mediaSource) {
        exoplayer.setMediaItem(mediaSource.mediaItem)
        exoplayer.prepare()
    }
}

@Preview
@Composable
private fun TranslateScreenPreview() {
    EduSignTheme {
        Surface {
            TranslateContent()
        }
    }
}