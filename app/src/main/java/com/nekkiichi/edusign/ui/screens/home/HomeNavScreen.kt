package com.nekkiichi.edusign.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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


sealed class HomeNavRoutes(var route: String, val icon: ImageVector?, var title: String) {
    data object Dashboard : HomeNavRoutes("app_dashboard", Icons.Rounded.Home, "Dashboard")
    data object Translate : HomeNavRoutes("app_translate", Icons.Rounded.Camera, "Translate")
    data object Notification :
        HomeNavRoutes("app_notification", Icons.Rounded.Notifications, "Notification")

    data object Account : HomeNavRoutes("app_account", Icons.Rounded.Person, "Account")
}


@Composable
fun HomeNavScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val bottomBarNavController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavBar(bottomNavController = bottomBarNavController)
    }) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            NavHost(
                navController = bottomBarNavController,
                startDestination = HomeNavRoutes.Dashboard.route
            ) {
                composable(HomeNavRoutes.Dashboard.route) {
                    DashboardScreen(bottomNavController = bottomBarNavController)
                }
                composable(HomeNavRoutes.Translate.route) {
                    TranslateScreen(navController = navController, homeViewModel)
                }
                composable(HomeNavRoutes.Notification.route) {
                    Text(text = "Notification")
                }
                composable(HomeNavRoutes.Account.route) {
                    Text(text = "Account")
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
        HomeNavRoutes.Dashboard,
        HomeNavRoutes.Translate,
        HomeNavRoutes.Notification,
        HomeNavRoutes.Account
    )
    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    var currentRoute by remember {
        mutableStateOf(HomeNavRoutes.Dashboard.route)
    }
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        items.forEachIndexed { index, navigationItem ->
            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
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
                        contentDescription = navigationItem.title
                    )
                },
                label = { Text(navigationItem.title) })
        }
    }
}

@Preview
@Composable
private fun HomeRootScreenPreview() {
    EduSignTheme {
        HomeNavScreen(navController = rememberNavController(), viewModel())
    }
}