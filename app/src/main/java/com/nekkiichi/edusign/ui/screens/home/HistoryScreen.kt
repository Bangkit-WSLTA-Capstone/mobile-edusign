@file:OptIn(ExperimentalMaterial3Api::class)

package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HistoryScreen(navController: NavController) {



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text("Histories")
            })
        }
    ) {
        Box(Modifier.padding(it)) {

        }
    }
}