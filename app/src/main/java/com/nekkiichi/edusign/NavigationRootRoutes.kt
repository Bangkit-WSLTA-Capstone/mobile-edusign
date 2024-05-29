package com.nekkiichi.edusign

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.screens.GuideScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object Welcome : Screen("welcome")
    object Dashboard : Screen("dashboard")
    object Translate : Screen("translate")
    object Profile : Screen("profile")
    object Settings : Screen("settings")

    fun withArgs(vararg arg: String): String {
        return buildString {
            append(route)
            arg.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

// home, login, register, welcome, dashboard, translate, profile, settings
@Composable
fun NavigationRootRoutes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable(Screen.Home.route) {
            Column {

            }
        }
        composable(Screen.Login.route) {
            Column {

            }
        }
        composable(Screen.Register.route) {
            Column {

            }
        }
        composable(Screen.Welcome.route) {
            GuideScreen(navigateToLogin = { navController.navigate("login") })
        }
        composable(Screen.Dashboard.route) {
            Column {

            }
        }
        composable(Screen.Translate.route) {
            Column {

            }
        }
        composable(Screen.Profile.route) {
            Column {

            }
        }
        composable(Screen.Settings.route) {
            Column {

            }
        }
    }
}
