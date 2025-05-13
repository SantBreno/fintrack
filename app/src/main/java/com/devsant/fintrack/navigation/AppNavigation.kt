package com.devsant.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.ui.screens.AddTransaction
import com.devsant.fintrack.ui.screens.HomeScreen

@Composable
fun AppNavigation(modifier : Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable("home") {
            HomeScreen(modifier, navController = navController)
        }
        composable("addTransaction") {
            AddTransaction(modifier, navController = navController)
        }
    })
}