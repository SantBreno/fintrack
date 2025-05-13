package com.devsant.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.ui.screens.AddTransactionScreen
import com.devsant.fintrack.ui.screens.HomeScreen
import com.devsant.fintrack.viewmodel.TransactionViewModel

@Composable
fun AppNavigation(modifier : Modifier = Modifier) {

    val navController = rememberNavController()
    val transactionViewModel: TransactionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home", builder = {
        composable("home") {
            HomeScreen(modifier, navController, transactionViewModel)
        }
        composable("addTransactionScreen") {
            AddTransactionScreen(modifier, navController = navController)
        }
    })
}