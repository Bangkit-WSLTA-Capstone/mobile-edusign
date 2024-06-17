@file:OptIn(ExperimentalMaterial3Api::class)

package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mikepenz.markdown.m3.Markdown
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.viewModel.MinicourseContentViewModel


@Composable
fun MinicourseScreen(navController: NavController, filename: String) {
    val minicourseContentViewModel: MinicourseContentViewModel = hiltViewModel()

    val minicourseState by minicourseContentViewModel.markdownString.collectAsState()

    LaunchedEffect(filename) {
        minicourseContentViewModel.fetchMarkdown(filename)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Mini Course") }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            })
        },

        ) {
        MinicourseContent(
            Modifier
                .padding(it)
                .fillMaxSize(), minicourseState)
    }
}

@Composable
private fun MinicourseContent(
    modifier: Modifier = Modifier,
    state: Status<String>
) {
    Box(modifier = modifier) {
        when (state) {
            is Status.Failed -> {
                Text(text = "Error: ${state.errorMessage}")
            }

            is Status.Success -> {
                Markdown(
                    content = state.value.trimIndent(),
                    modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
                )
            }

            else -> {
                Column(Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator()
                    Text(text = "Loading...")
                }
            }
        }

    }

}

@Preview
@Composable
fun MinicoursePreview(dark: Boolean = false) {
    val templateMarkdown = """
        hello brow
    """.trimIndent()
    EduSignTheme(darkTheme = dark) {
        MinicourseContent(state = Status.Success(templateMarkdown))
    }
}