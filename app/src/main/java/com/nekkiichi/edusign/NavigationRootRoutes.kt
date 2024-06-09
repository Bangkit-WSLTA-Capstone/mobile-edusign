package com.nekkiichi.edusign

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.screens.WelcomeScreen
import com.nekkiichi.edusign.ui.screens.auth.LoginScreen
import com.nekkiichi.edusign.ui.screens.auth.RegisterScreen
import com.nekkiichi.edusign.ui.screens.home.HomeRootScreen


sealed class RootNavRoutes(val route: String) {
    object Home : RootNavRoutes("home")
    object Login : RootNavRoutes("login")
    object Register : RootNavRoutes("register")
    object Welcome : RootNavRoutes("welcome")

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

    NavHost(navController = navController, startDestination = RootNavRoutes.Welcome.route) {
        composable(RootNavRoutes.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(RootNavRoutes.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(RootNavRoutes.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(RootNavRoutes.Home.route) {
            HomeRootScreen(navController = navController)
        }
    }
}
