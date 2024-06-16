@file:OptIn(ExperimentalMaterial3Api::class)

package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mikepenz.markdown.m3.Markdown
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.viewModel.MinicourseContentViewModel


@Composable
fun MinicourseScreen(navController: NavController, filename: String) {
    val minicourseContentViewModel: MinicourseContentViewModel = hiltViewModel()

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
        MinicourseContent(Modifier.padding(it), minicourseContentViewModel)
    }
}

@Composable
private fun MinicourseContent(
    modifier: Modifier = Modifier,
    minicourseContentViewModel: MinicourseContentViewModel
) {
    Box(modifier = modifier) {
        when (val state = minicourseContentViewModel.markdownString) {
            is Status.Failed -> {
                Text(text = "Error: ${state.errorMessage}")
            }

            is Status.Success -> {
                Markdown(
                    content = state.value.trimIndent(),
                    modifier = Modifier.verticalScroll(rememberScrollState())
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
    EduSignTheme(darkTheme = dark) {
        MinicourseScreen(navController = rememberNavController(), viewModel())
    }
}