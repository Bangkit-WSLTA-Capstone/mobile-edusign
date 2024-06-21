package com.nekkiichi.edusign.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.RootRoutes
import com.nekkiichi.edusign.data.entities.TranslateHistory
import com.nekkiichi.edusign.ui.components.ModalTranslateContent
import com.nekkiichi.edusign.ui.components.ModalTranslateError
import com.nekkiichi.edusign.ui.components.ModalTranslateLoading
import com.nekkiichi.edusign.ui.components.TranslateHistoryItem
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.composable.SecondaryButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.viewModel.HistoryViewModel
import com.nekkiichi.edusign.viewModel.HomeViewModel
import com.nekkiichi.edusign.viewModel.TranslateViewModel
import java.io.File
import java.time.Instant
import java.util.Date

object TranslateScreen {
    const val VIDEO_FILE = "video_file"
}

private val tabTitles = listOf("Video", "History")

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslateScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val translateViewModel: TranslateViewModel = hiltViewModel()

    var tabState by remember { mutableIntStateOf(0) }
    val sheetState = rememberModalBottomSheetState()

    fun showCamera() {
        navController.navigate(RootRoutes.Camera.route)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Live Translate") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        )
    }) {
        Column(
            Modifier
                .padding(it)
        ) {
            PrimaryTabRow(
                selectedTabIndex = tabState,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ) {
                tabTitles.forEachIndexed { index, s ->
                    Tab(
                        selected = tabState == index,
                        onClick = { tabState = index },
                        Modifier.defaultMinSize(minHeight = 36.dp)
                    ) {
                        Text(text = s, minLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            when (tabState) {
                0 -> {
                    if (homeViewModel.videoFile != null) {
                        VideoViewer(
                            Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .height(450.dp),
                            file = homeViewModel.videoFile
                        )
                    } else {
                        Surface(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(450.dp)
                            .clip(RoundedCornerShape(8.dp)),
                            tonalElevation = 4.dp,
                            onClick = {
                                showCamera()
                            }) {
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
                    Row(
                        Modifier.padding(horizontal = 16.dp)
                    ) {
                        PrimaryButton(
                            onCLick = {
                                val video = homeViewModel.videoFile ?: return@PrimaryButton
                                translateViewModel.translateVideo(video)
                            },
                            Modifier
                                .weight(3f),
                            enabled = homeViewModel.videoFile != null && translateViewModel.translateResult == null
                        ) {
                            Text(text = "TRANSLATE")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        SecondaryButton(
                            onCLick = {
                                homeViewModel.clearVideo()
                            },
                            enabled = homeViewModel.videoFile != null
                        ) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Rounded.Delete, contentDescription = "Delete")
                        }
                    }
                }

                else -> {
                    HistoryTabScreen(Modifier.fillMaxSize())
                }
            }
        }
        if (translateViewModel.translateResult != null) {
            ModalBottomSheet(onDismissRequest = {
                translateViewModel.translateResult = null
            }, sheetState = sheetState) {
                when (val result = translateViewModel.translateResult) {
                    is Status.Success -> {
                        ModalTranslateContent(text = result.value.data?.result ?: "")
                    }

                    is Status.Failed -> {
                        ModalTranslateError(text = result.errorMessage)
                    }

                    else -> {
                        ModalTranslateLoading()
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryTabScreen(modifier: Modifier = Modifier) {
    val historyViewModel: HistoryViewModel = hiltViewModel()
    val historyState by historyViewModel.historyState
    Box(modifier = modifier) {
        when (val state = historyState) {
            is Status.Loading -> {
                LinearProgressIndicator(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }

            is Status.Success -> {
                val histories = state.value
                if (histories.isEmpty()) {
                    Text(text = "Empty History", Modifier.align(Alignment.Center))
                }
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(histories) { item: TranslateHistory ->
                        TranslateHistoryItem(title = item.result, date = item.dateCreated)
                    }
                }
            }

            else -> {}
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun VideoViewer(modifier: Modifier = Modifier, file: File?) {
    val context = LocalContext.current
    val exoplayer = ExoPlayer.Builder(context).build()

    LaunchedEffect(file) {
        if (file != null) {
            val defaultHttpDataSource = DefaultHttpDataSource.Factory()
            val uri = Uri.fromFile(file)
            val mediaItem = androidx.media3.common.MediaItem.fromUri(uri)
            val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSource)
                .createMediaSource(mediaItem)
            exoplayer.setMediaItem(mediaSource.mediaItem)
            exoplayer.prepare()
        } else {
            exoplayer.release()
        }
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
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    )
}

@Preview()
@Composable
private fun TranslateScreenPreview() {
    EduSignTheme {
        Surface {
            TranslateScreen(rememberNavController(), viewModel())
        }
    }
}

@Preview
@Composable
private fun HistoryListPreview() {
    val dummyData = listOf(
        TranslateHistory(
            dateCreated = Date.from(Instant.now()),
            fileURl = "thisisurl",
            result = "Aloha"
        ),TranslateHistory(
            dateCreated = Date.from(Instant.now()),
            fileURl = "thisisurl",
            result = "Aloha"
        ),TranslateHistory(
            dateCreated = Date.from(Instant.now()),
            fileURl = "thisisurl",
            result = "Aloha"
        ),
    )
    EduSignTheme {
        Surface {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(dummyData) { item: TranslateHistory ->
                    TranslateHistoryItem(title = item.result, date = item.dateCreated)
                }
            }
        }
    }
}

@Preview()
@Composable
private fun TranslateScreenPreviewDark() {
    EduSignTheme(darkTheme = true) {
        Surface {
            TranslateScreen(rememberNavController(), viewModel())
        }
    }
}