package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nekkiichi.edusign.RootRoutes
import com.nekkiichi.edusign.data.remote.response.CourseItem
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.viewModel.MinicoursesViewModel

internal class MinicoursesHandler {
    var refresh: () -> Unit = {}
    var back: () -> Unit = {}
    var navigateToCourse: (filename: String) -> Unit = {}
}

@Composable
fun MinicoursesScreen(navController: NavController) {
    val minicoursesViewModel: MinicoursesViewModel = hiltViewModel()
    val minicoursesState by minicoursesViewModel.minicoursesState.collectAsState()

    val handler = MinicoursesHandler().apply {
        refresh = {
            minicoursesViewModel.fetchMinicourses()
        }
        back = {
            navController.navigateUp()
        }
        navigateToCourse = { filename: String ->
            navController.navigate(RootRoutes.Minicourse.withFilename(filename))
        }
    }

    MinicoursesContent(state = minicoursesState, handler)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MinicoursesContent(
    state: com.nekkiichi.edusign.utils.Status<List<CourseItem>>,
    handler: MinicoursesHandler
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Mini Course List") }, navigationIcon = {
                IconButton(onClick = { handler.back.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            })
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (state) {
                is Status.Failed -> {
                    Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Failed to get courses")
                        Text(text = state.errorMessage)
                    }
                }

                is Status.Success -> {
                    if (state.value.isEmpty()) {
                        Column(
                            Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Course isn't available right now")
                            Text(text = "Stay tuned for the updates")
                        }
                    }
                    LazyColumn(
                        Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    ) {
                        items(state.value) { item ->
                            ListItem(
                                headlineContent = { Text(text = item.title) },
                                Modifier.clickable {
                                    handler.navigateToCourse.invoke(item.coursename)
                                },
                                overlineContent = {
                                    Text(
                                        text = item.createdAt
                                    )
                                },
                                supportingContent = {
                                    Text(text = item.description ?: "", overflow = TextOverflow.Ellipsis, maxLines = 2)
                                },

                            )
                        }
                    }
                }

                else -> {
                    Column(
                        Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text(text = "Fetching...")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MinicourseScreenPreview() {
    EduSignTheme {
        MinicoursesContent(
            state = Status.Success(
                listOf(
                    CourseItem(
                        "test.md",
                        "28 feb",
                        "Welcome",
                        "this is description"
                    )
                )
            ),
            handler = MinicoursesHandler()
        )
    }
}