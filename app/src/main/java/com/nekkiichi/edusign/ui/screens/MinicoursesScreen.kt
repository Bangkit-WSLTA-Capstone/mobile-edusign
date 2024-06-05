@file:OptIn(ExperimentalMaterial3Api::class)

package com.nekkiichi.edusign.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun MinicoursesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "MINI COURSE") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            })
        },

        ) {
        MinicoursesContent(Modifier.padding(it))
    }
}

@Composable
private fun MinicoursesContent(modifier: Modifier = Modifier) {

}

@Preview
@Composable
fun MinicoursePreview(dark: Boolean = false) {
    EduSignTheme(darkTheme = dark) {
        MinicoursesScreen(navController = rememberNavController())
    }
}