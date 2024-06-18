package com.nekkiichi.edusign

import android.util.Log
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
import com.nekkiichi.edusign.ui.screens.home.NavigationHome
import com.nekkiichi.edusign.ui.screens.home.SignWordsScreen
import com.nekkiichi.edusign.ui.screens.home.TranslateScreen
import com.nekkiichi.edusign.utils.extension.popUpToTop
import com.nekkiichi.edusign.viewModel.AuthViewModel
import com.nekkiichi.edusign.viewModel.HomeViewModel
import java.io.File


sealed class RootRoutes(val route: String) {
    object Init : RootRoutes("/init")
    object Home : RootRoutes("/home")
    object Login : RootRoutes("/login")
    object Register : RootRoutes("/register")
    object Welcome : RootRoutes("/welcome")
    object Camera : RootRoutes("/camera")
    object Menu : RootRoutes("/menu")
    object Dictionary : RootRoutes("/dictionary")
    object Minicourse : RootRoutes("/minicourse") {
        fun withFilename(filename: String) = "$route/$filename"
        fun _composable() = "$route/{filename}"
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
            Log.d("RootScreen", "Logout emitted")
            navController.navigate(RootRoutes.Login.route) {
                popUpToTop(navController)
            }
        }
    }

    // show welcome screen only once, and then redirect to login afterwards
    LaunchedEffect(Unit) {
        authViewModel.welcomeEvent.collect {
            navController.navigate(RootRoutes.Welcome.route) {
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
            NavigationHome(navController, homeViewModel)
        }

        composable(RootRoutes.Dictionary.route) {
            SignWordsScreen(navController)
        }

//        composable(RootRoutes.Camera.route) {
//            CameraScreen(navController)
//        }
//
//        composable(RootRoutes.Minicourse.route) {
//            MinicoursesScreen(navController)
//        }
//
//        composable(
//            RootRoutes.Minicourse._composable(),
//            arguments = listOf(navArgument("filename") { type = NavType.StringType })
//        ) {
//            MinicourseScreen(navController, it.arguments?.getString("filename") ?: "")
//        }
//
//        composable(RootRoutes.Menu.route) {
//            MenuScreen(navController)
//        }
    }
}
