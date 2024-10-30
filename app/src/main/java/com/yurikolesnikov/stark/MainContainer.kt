package com.yurikolesnikov.stark

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainContainer() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.MAIN_SCREEN.screenName
    ) {
        composable(Destination.MAIN_SCREEN.screenName) { MainScreen(navController) }
        composable(Destination.WEB_VIEW_SCREEN.screenName,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
            }) {
            WebViewScreen(navController)
        }
    }
}