package com.nekkiichi.edusign.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.RootRoutes
import com.nekkiichi.edusign.ui.composable.BentoButton
import com.nekkiichi.edusign.ui.composable.DashboardButton
import com.nekkiichi.edusign.ui.theme.EduSignTheme


@Composable
fun DashboardScreen(navController: NavController, bottomNavController: NavController) {

    fun navigateToTranslate() {
        bottomNavController.navigate(HomeRoutes.Translate.route) {
            bottomNavController.graph.startDestinationRoute?.let {
                popUpTo(it) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun  navigateToMiniCourse() {
        navController.navigate(RootRoutes.Minicourse.route)
    }

    fun navigateToGlossary() {
        navController.navigate(RootRoutes.Dictionary.route)
    }

    Scaffold {
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.padding(it)) {
                // HERO SECTION
                Surface(tonalElevation = 4.dp) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Hello,",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = "Customer",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(24.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            BentoButton(onClick = {
                                navigateToMiniCourse()
                            }, modifier = Modifier.weight(1f)) {
                                Column(Modifier.padding(12.dp)) {
                                    Icon(
                                        imageVector = Icons.Rounded.TaskAlt,
                                        contentDescription = "Mini Course Icon"
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Column(
                                        Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = "Want to learn?",
                                            style = MaterialTheme.typography.bodySmall,
                                            minLines = 2
                                        )
                                        Text(
                                            text = "Mini Course",
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                            BentoButton(
                                onClick = {
                                    navigateToGlossary()
                                },
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.tertiary
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Icon(
                                        imageVector = Icons.Rounded.LocalLibrary,
                                        contentDescription = "Mini Course Icon"
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Column(
                                        Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = "Sign Language directory",
                                            style = MaterialTheme.typography.bodySmall,
                                            minLines = 2
                                        )
                                        Text(
                                            text = "Glossary",
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


                // CONTENT SECTION
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "Live Translator",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Everything can be your tools")

                    //Button Section
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        DashboardButton(
                            onClick = {
                                navigateToTranslate()
                            },
                            icon = {
                                Icon(
                                    Icons.Rounded.CameraAlt,
                                    contentDescription = null
                                )
                            }, label = "Open Camera", description = "Video translate"
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Contributes",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Help us to improve our product!")
                    Spacer(modifier = Modifier.size(16.dp))
                    DashboardButton(onClick = {}, label = "Link to repository")
                }
            }
        }
    }

}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DashboardScreenPreview() {
    EduSignTheme {
        DashboardScreen(bottomNavController = rememberNavController(), navController = rememberNavController())
    }
}