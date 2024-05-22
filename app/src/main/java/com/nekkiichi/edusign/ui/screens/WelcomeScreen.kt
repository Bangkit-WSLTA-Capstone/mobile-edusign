package com.nekkiichi.edusign.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nekkiichi.edusign.ui.composable.PrimaryButton
import com.nekkiichi.edusign.ui.composable.TextButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuideScreen() {
    val pagerState = rememberPagerState(pageCount = {
        3
    })

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
            Row(Modifier.align(Alignment.TopEnd)) {
                TextButton(onCLick = { /*TODO*/ }) {
                    Text(text = "Skip ->")
                }
            }
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                GuidePage1()
                GuidePage2()
                GuidePage3()
            }
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageIndicator(pagerState)
                PrimaryButton(onCLick = { /*TODO*/ }) {
                    Text(text = "NEXT >>>")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Already have account?")
                    TextButton(onCLick = { /*TODO*/ }, Modifier) {
                        Text(
                            text = "Login Here",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageIndicator(pagerState: PagerState) {
    Row(
        Modifier
            .wrapContentWidth()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { pageIdx ->
            val color =
                if (pagerState.currentPage <= pageIdx) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
            )
        }
    }
}


@Preview
@Composable
private fun GuideScreenPreview() {
    EduSignTheme(dynamicColor = false) {
        GuideScreen()
    }
}


@Composable
private fun GuidePage1() {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Welcome to EduSign, American Sign Translation app with mini course included!")
    }
}

@Composable
private fun GuidePage2() {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome 2", style = MaterialTheme.typography.headlineLarge)
        Text(text = "You can translate your sign language on the go!")
    }
}

@Composable
private fun GuidePage3() {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome 2", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Use Mini-Courses to learn more about sign language!")
    }
}