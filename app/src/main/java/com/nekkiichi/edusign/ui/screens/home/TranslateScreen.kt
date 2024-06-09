package com.nekkiichi.edusign.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.RootNavRoutes
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.viewModel.HomeViewModel

object TranslateScreen {
    const val VIDEO_FILE = "video_file"
}

private val tabTitles = listOf("Video", "Text")

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslateScreen(navController: NavController, homeViewModel: HomeViewModel) {
    var tabState by remember { mutableStateOf(0) }
    fun showCamera() {
        navController.navigate(RootNavRoutes.Camera.route)
    }
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) {

        Column(
            Modifier
                .padding(it)
        ) {
            PrimaryTabRow(
                selectedTabIndex = tabState,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                tabTitles.forEachIndexed { index, s ->
                    Tab(selected = tabState == index, onClick = { tabState = index }) {
                        Text(text = s, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            when (tabState) {
                0 -> {
                    if (homeViewModel.videoFile != null) {
                        val defaultHttpDataSource = DefaultHttpDataSource.Factory()
                        val file = homeViewModel.videoFile!!
                        val uri = Uri.fromFile(file)
                        val mediaItem = androidx.media3.common.MediaItem.fromUri(uri)
                        val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSource)
                            .createMediaSource(mediaItem)
                        VideoViewer(
                            Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            mediaSource = mediaSource
                        )
                    } else {
                        Surface(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            tonalElevation = 4.dp,
                            onClick = {
                                showCamera()
                            }
                        ) {

                            Box {
                                Box(modifier = Modifier.align(Alignment.Center)) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Outlined.Camera,
                                            contentDescription = "",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.size(2.dp))
                                        Text(text = "Open Camera")
                                    }
                                }
                            }
                        }
                    }

                }

                else -> {
                    TextField(value = "", onValueChange = {})
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
            TranslateScreen(rememberNavController(), viewModel())
        }
    }
}