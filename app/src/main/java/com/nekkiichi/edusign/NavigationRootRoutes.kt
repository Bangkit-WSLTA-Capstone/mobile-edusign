package com.nekkiichi.edusign

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.screens.SplashScreen
import com.nekkiichi.edusign.ui.screens.WelcomeScreen
import com.nekkiichi.edusign.ui.screens.auth.LoginScreen
import com.nekkiichi.edusign.ui.screens.auth.RegisterScreen
import com.nekkiichi.edusign.ui.screens.home.HomeNavScreen
import com.nekkiichi.edusign.ui.screens.home.TranslateScreen
import com.nekkiichi.edusign.ui.screens.home.camera.CameraScreen
import com.nekkiichi.edusign.utils.extension.popUpToTop
import com.nekkiichi.edusign.viewModel.AuthViewModel
import com.nekkiichi.edusign.viewModel.HomeViewModel
import java.io.File


sealed class RootRoutes(val route: String) {
    object Init : RootRoutes("init")
    object Home : RootRoutes("home")
    object Login : RootRoutes("login")
    object Register : RootRoutes("register")
    object Welcome : RootRoutes("welcome")
    object Camera : RootRoutes("camera")

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

    val authViewModel: AuthViewModel = hiltViewModel()


    LaunchedEffect(Unit) {
        authViewModel.logoutEvent.collect {
            navController.navigate(RootRoutes.Login.route) {
                popUpToTop(navController)
            }
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.loginEvent.collect {
            navController.navigate(RootRoutes.Home.route) {
                popUpToTop(navController)
            }
        }
    }

    NavHost(navController = navController, startDestination = RootRoutes.Init.route) {
        composable(RootRoutes.Init.route) {
            SplashScreen()
        }
        composable(RootRoutes.Login.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(RootRoutes.Register.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(RootRoutes.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(RootRoutes.Home.route) {
            homeViewModel.videoFile = it.savedStateHandle.get<File>(TranslateScreen.VIDEO_FILE)
            HomeNavScreen(navController, homeViewModel)
        }
        composable(RootRoutes.Camera.route) {
            CameraScreen(navController)
        }
    }
}
