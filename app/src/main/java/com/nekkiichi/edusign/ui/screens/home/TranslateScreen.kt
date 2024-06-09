package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.viewModel.TranslateViewModel
import java.io.File

object TranslateScreen {
    const val VIDEO_FILE = "video_file"
}

@Composable
fun TranslateScreen(translateViewModel: TranslateViewModel = viewModel()) {
    TranslateContent(videoFile = translateViewModel.videoFile)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TranslateContent(videoFile: File? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Live Translate") },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Rounded.History,
                            contentDescription = "Share"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Video Sources", style = MaterialTheme.typography.titleSmall)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(200.dp, 300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow),
            ) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun VideoViewer(modifier: Modifier = Modifier, mediaSource: MediaSource) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val exoplayer = ExoPlayer.Builder(context).build()

    LaunchedEffect(mediaSource) {
        exoplayer.setMediaItem(mediaSource.mediaItem)
        exoplayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoplayer.release()
        }
    }
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoplayer

            }
        },
        Modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun TranslateScreenPreview() {
    EduSignTheme {
        Surface {
            TranslateContent(null)
        }
    }
}