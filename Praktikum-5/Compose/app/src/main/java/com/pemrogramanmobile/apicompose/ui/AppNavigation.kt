package com.pemrogramanmobile.apicompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemrogramanmobile.apicompose.ui.screen.moviedetail.MovieDetailScreen
import com.pemrogramanmobile.apicompose.ui.screen.movielist.MovieListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MOVIE_LIST) {
        composable(Routes.MOVIE_LIST) {
            MovieListScreen(navController = navController)
        }
        composable(
            route = Routes.MOVIE_DETAIL,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) {
            MovieDetailScreen(navController = navController)
        }
    }
}