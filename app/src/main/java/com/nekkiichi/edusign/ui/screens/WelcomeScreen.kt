@file:OptIn(ExperimentalAnimationApi::class)

package com.nekkiichi.edusign.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mikepenz.markdown.m3.Markdown
import com.nekkiichi.edusign.R
import com.nekkiichi.edusign.RootRoutes
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.composable.TextButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import kotlinx.coroutines.launch

@Immutable
private data class Guide(
    val title: String? = null, val description: String, @DrawableRes val imageDrawable: Int? = null
)

private val guides = listOf(
    Guide(
        "Break down barriers. \nLearn sign language \nwith us!",
        "Welcome to __EduSign__, American Sign Translation app with mini course included!"
    ),
    Guide(
        "Woah!", "You can translate your sign language on the go!", R.drawable.welcome_translator
    ),
    Guide(
        "Also,", "Use Mini-Courses to learn more about sign language!", R.drawable.welcome_course
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {

    val pagerState = rememberPagerState(pageCount = {
        guides.size
    })
    val beyondBoundsPageCount = 1
    val coroutineScope = rememberCoroutineScope()

    var isSkipButtonEnabled by remember { mutableStateOf(true) }

    fun skipToEnd() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(2)
        }
    }

    fun navigateToLogin() {
        navController.navigate(RootRoutes.Login.route) {
            popUpTo(RootRoutes.Welcome.route) {
                inclusive = true
            }
        }
    }

    fun navigateToRegister() {
        navController.navigate(RootRoutes.Register.route) {
            popUpTo(RootRoutes.Welcome.route) {
                inclusive = true
            }
        }
    }

    fun nextPage() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            isSkipButtonEnabled = page < pagerState.pageCount - 1
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
                    visible = isSkipButtonEnabled,
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
                state = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 48.dp),
                beyondViewportPageCount = beyondBoundsPageCount
            ) {

                with(guides[it]) {
                    GuideItem(
                        title = title ?: "", description = description, drawableRes = imageDrawable
                    )
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
                if (isSkipButtonEnabled) {
                    PrimaryButton(
                        onCLick = {
                            nextPage()
                        }, Modifier.fillMaxWidth()
                    ) {
                        Text(text = "NEXT")
                    }
                } else {
                    PrimaryButton(
                        onCLick = {
                            navigateToRegister()
                        }, Modifier.fillMaxWidth()
                    ) {
                        Text(text = "START NOW")
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Already have account?")
                    TextButton(
                        onCLick = { navigateToLogin() }, Modifier
                    ) {
                        Text(
                            text = "Login", style = MaterialTheme.typography.bodyLarge.copy(
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
                isSelected = pagerState.currentPage >= pageIdx, modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PageIndicatorItem(
    modifier: Modifier = Modifier, isSelected: Boolean, durationInMilis: Int = 250
) {
//    val color =
    val color: Color by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
        animationSpec = tween(durationMillis = durationInMilis),
        label = "PageIndicatorItemColor"
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
private fun GuideItem(title: String, description: String, @DrawableRes drawableRes: Int? = null) {
    GuideItemLayout {
        Text(text = title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.size(8.dp))
        Markdown(content = description, modifier = Modifier.wrapContentHeight())
        if (drawableRes != null) {
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painterResource(id = drawableRes), contentDescription = "", Modifier.size(200.dp),

            )

        }

    }

}
