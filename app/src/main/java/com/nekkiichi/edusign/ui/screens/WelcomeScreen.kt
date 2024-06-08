@file:OptIn(ExperimentalAnimationApi::class)

package com.nekkiichi.edusign.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.Screen
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.composable.TextButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import kotlinx.coroutines.launch

private data class Guide(val title: String? = null, val description: String)

private val guides = listOf(
    Guide(
        "Welcome",
        "Welcome to EduSign, American Sign Translation app with mini course included!"
    ),
    Guide("Woah!", "You can translate your sign language on the go!"),
    Guide("Also,", "Use Mini-Courses to learn more about sign language!"),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = {
        guides.size
    })
    val coroutineScope = rememberCoroutineScope()

    var isSkipButtonEnabled by remember { mutableStateOf(true) }

    fun skipToEnd() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(2)
        }
    }

    fun nextPage() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            isSkipButtonEnabled = page > 2
        }
    }


    Scaffold(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Row(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp)
            ) {
                AnimatedVisibility(
                    visible = pagerState.currentPage < pagerState.pageCount - 1,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    TextButton(onCLick = {
                        skipToEnd()
                    }) {
                        Text(text = "Skip ->")
                    }

                }
            }
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                with(guides[it]) {
                    GuideItem(title = title ?: "", description = description)
                }
            }
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageIndicator(pagerState)
                AnimatedContent(
                    targetState = pagerState.currentPage < pagerState.pageCount - 1, label = "",
                    transitionSpec = {
                        if (targetState) {
                            (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                slideOutHorizontally { width -> -width } + fadeOut())
                        } else {
                            (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                slideOutHorizontally { width -> width } + fadeOut())
                        }.using(SizeTransform(clip = false))
                    },
                    contentAlignment = Alignment.Center
                ) {
                    if (it) {
                        PrimaryButton(
                            onCLick = {
                                nextPage()
                            },
                        ) {
                            Text(text = "NEXT")
                        }
                    } else {
                        PrimaryButton(
                            onCLick = {
                                navController.navigate(Screen.Register.route) {
                                    popUpTo(0)
                                }
                            },
                        ) {
                            Text(text = "START")
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Already have account?")
                    TextButton(onCLick = { navController.navigate(Screen.Login.route) }, Modifier) {
                        Text(
                            text = "Login Here",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    Row(
        Modifier
            .wrapContentWidth()
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { pageIdx ->
            PageIndicatorItem(
                isSelected = pagerState.currentPage >= pageIdx,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PageIndicatorItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    durationInMilis: Int = 250
) {
//    val color =
    val color: Color by animateColorAsState(
        targetValue =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
        animationSpec = tween(durationMillis = durationInMilis), label = "PageIndicatorItemColor"
    )
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape)
            .background(color)
            .defaultMinSize(minWidth = 16.dp, minHeight = 8.dp)
            .then(modifier)
    )
}


@Preview
@Composable
private fun GuideScreenPreview(dark: Boolean = false) {
    EduSignTheme(darkTheme = dark) {
        WelcomeScreen(navController = rememberNavController())
    }
}

@Preview
@Composable
private fun GuideScreenPreviewDark() {
    GuideScreenPreview(true)
}

@Composable
private fun GuideItemLayout(content: @Composable (ColumnScope.() -> Unit)) {
    Column(
        Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}


@Composable
private fun GuideItem(title: String, description: String) {
    GuideItemLayout {
        Text(text = title, style = MaterialTheme.typography.headlineLarge)
        Text(text = description)
    }

}
