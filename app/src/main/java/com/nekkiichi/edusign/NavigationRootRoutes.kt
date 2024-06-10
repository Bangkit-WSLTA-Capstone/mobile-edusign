package com.nekkiichi.edusign

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.screens.WelcomeScreen
import com.nekkiichi.edusign.ui.screens.auth.LoginScreen
import com.nekkiichi.edusign.ui.screens.auth.RegisterScreen
import com.nekkiichi.edusign.ui.screens.home.HomeNavScreen
import com.nekkiichi.edusign.ui.screens.home.TranslateScreen
import com.nekkiichi.edusign.ui.screens.home.camera.CameraScreen
import com.nekkiichi.edusign.viewModel.HomeViewModel
import java.io.File


sealed class RootNavRoutes(val route: String) {
    object Home : RootNavRoutes("home")
    object Login : RootNavRoutes("login")
    object Register : RootNavRoutes("register")
    object Welcome : RootNavRoutes("welcome")
    object Camera : RootNavRoutes("camera")

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
    val homeViewModel: HomeViewModel = viewModel()
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
            homeViewModel.videoFile = it.savedStateHandle.get<File>(TranslateScreen.VIDEO_FILE)
            HomeNavScreen(navController = navController, homeViewModel)
        }
        composable(RootNavRoutes.Camera.route) {
            CameraScreen(navController = navController)
        }
    }
}
