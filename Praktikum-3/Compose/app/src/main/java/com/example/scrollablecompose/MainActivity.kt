package com.example.scrollablecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scrollablecompose.screens.DetailScreen
import com.example.scrollablecompose.screens.ListScreen
import com.example.scrollablecompose.ui.theme.ScrollableComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrollableComposeTheme {
                MyFavKamenRiderApp()
            }
        }
    }
}

@Composable
fun MyFavKamenRiderApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.listScreen, builder = {
        composable(Routes.listScreen) {
            ListScreen(navController)
        }
        composable(Routes.detailScreen+"/{id}") {
            val idString = it.arguments?.getString("id")
            val id = idString?.toIntOrNull() ?: 0
            DetailScreen(navController, id)
        }
    })
}





