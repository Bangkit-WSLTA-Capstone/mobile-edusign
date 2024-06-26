package com.nekkiichi.edusign.ui.screens.home

import android.content.res.Configuration
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalLibrary
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nekkiichi.edusign.ui.theme.EduSignTheme
import com.nekkiichi.edusign.viewModel.HomeViewModel


sealed class HomeRoutes(var route: String, val icon: ImageVector?, var title: String) {
    data object Dashboard : HomeRoutes("app_dashboard", Icons.Rounded.Home, "Dashboard")
    data object Translate : HomeRoutes("app_translate", Icons.Rounded.Camera, "Translate")
    data object Dictionary :
        HomeRoutes("app_dictionary", Icons.Rounded.LocalLibrary, "Dictionary")
    data object Menu : HomeRoutes("app_menu", Icons.Rounded.Person, "Menu")
}


@Composable
fun NavigationHome(navController: NavController, homeViewModel: HomeViewModel) {
    val bottomBarNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavBar(bottomNavController = bottomBarNavController)
    }) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            NavHost(
                navController = bottomBarNavController,
                startDestination = HomeRoutes.Dashboard.route,
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                }
            ) {
                composable(HomeRoutes.Dashboard.route) {
                    DashboardScreen(navController, bottomBarNavController)
                }
                composable(HomeRoutes.Translate.route) {
                    TranslateScreen(navController, homeViewModel)
                }
                composable(HomeRoutes.Dictionary.route) {
                    SignWordsScreen(navController = navController)
                }
                composable(HomeRoutes.Menu.route) {
//                    Text(text = "Menu")
                    MenuScreen(navController)
                }

            }
        }
    }
}


@Composable
private fun BottomNavBar(
    modifier: Modifier = Modifier, bottomNavController: NavHostController
) {
    val items = listOf(
        HomeRoutes.Dashboard,
        HomeRoutes.Translate,
        HomeRoutes.Dictionary,
        HomeRoutes.Menu
    )
    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    var currentRoute by remember {
        mutableStateOf(HomeRoutes.Dashboard.route)
    }
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(modifier = modifier, containerColor = MaterialTheme.colorScheme.secondary) {
        items.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
                onClick = {
                    selectedItem = index
                    currentRoute = navigationItem.route
                    bottomNavController.navigate(navigationItem.route) {
                        bottomNavController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = navigationItem.icon!!,
                        contentDescription = navigationItem.title,
                        tint = Color.White
                    )
                },
                label = { Text(text = navigationItem.title, color = Color.White) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Transparent,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeRootScreenPreview() {
    EduSignTheme {
        NavigationHome(navController = rememberNavController(),  viewModel())
    }
}