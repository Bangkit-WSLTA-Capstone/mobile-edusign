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
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.Surface
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
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.composable.TextButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuideScreen(modifier: Modifier = Modifier, navigateToLogin: () -> Unit = {}) {
    val pageList: List<@Composable RowScope.() -> Unit> =
        listOf({ GuideItem1() }, { GuideItem2() }, { GuideItem3() })
    val pagerState = rememberPagerState(pageCount = {
        pageList.size
    })
    val coroutineScope = rememberCoroutineScope()

    var isSkipButtonEnabled by remember { mutableStateOf(true) }

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
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
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
                when (it) {
                    0 -> GuideItem1()
                    1 -> GuideItem2()
                    2 -> GuideItem3()
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
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                        ) {
                            Text(text = "NEXT")
                        }
                    } else {
                        PrimaryButton(
                            onCLick = { /*TODO*/ },
                        ) {
                            Text(text = "Start")
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Already have account?")
                    TextButton(onCLick = { navigateToLogin() }, Modifier) {
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
        GuideScreen(navigateToLogin = {});
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
private fun GuideItem1() {
    GuideItemLayout {
        Text(text = "Welcome", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Welcome to EduSign, American Sign Translation app with mini course included!")
    }

}

@Preview
@Composable
private fun GuideItem1Preview() {
    EduSignTheme(dynamicColor = false) {
        Surface {
            GuideItem1()
        }
    }
}

@Composable
private fun GuideItem2() {
    GuideItemLayout {
        Text(text = "Woah", style = MaterialTheme.typography.headlineLarge)
        Text(text = "You can translate your sign language on the go!")
    }
}

@Preview
@Composable
private fun GuideItem2Preview() {
    EduSignTheme(dynamicColor = false) {
        Surface {
            GuideItem2()
        }
    }
}

@Composable
private fun GuideItem3() {
    GuideItemLayout {
        Text(text = "Also,", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Use Mini-Courses to learn more about sign language!")
    }
}

@Preview
@Composable
private fun GuideItem3Preview() {
    EduSignTheme(dynamicColor = false) {
        Surface {
            GuideItem3()
        }
    }
}