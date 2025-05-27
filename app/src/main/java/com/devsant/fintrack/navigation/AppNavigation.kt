package com.devsant.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devsant.fintrack.ui.screens.AddTransactionScreen
import com.devsant.fintrack.ui.screens.ExpenseDetailScreen
import com.devsant.fintrack.ui.screens.HomeScreen
import com.devsant.fintrack.ui.screens.TransactionDetailScreen
import com.devsant.fintrack.viewmodel.TransactionViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val transactionViewModel: TransactionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(modifier, navController, transactionViewModel)
        }
        composable("addTransactionScreen") {
            AddTransactionScreen(modifier, navController, transactionViewModel)
        }
        composable(
            route = "transactionDetailScreen/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: -1
            TransactionDetailScreen(
                transactionId = transactionId,
                navController = navController,
                viewModel = transactionViewModel
            )
        }
        composable("expenseDetailScreen") {
            ExpenseDetailScreen(modifier, navController)
        }
    }
}