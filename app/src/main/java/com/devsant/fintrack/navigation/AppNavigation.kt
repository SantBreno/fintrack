package com.devsant.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devsant.fintrack.data.AppDatabase
import com.devsant.fintrack.ui.screens.AddTransactionScreen
import com.devsant.fintrack.ui.screens.ExpenseDetailScreen
import com.devsant.fintrack.ui.screens.HomeScreen
import com.devsant.fintrack.ui.screens.IncomeDetailScreen
import com.devsant.fintrack.ui.screens.TransactionDetailScreen
import com.devsant.fintrack.viewmodel.TransactionViewModel
import com.devsant.fintrack.viewmodel.TransactionViewModelFactory

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    database: AppDatabase
) {
    val navController = rememberNavController()
    val viewModel: TransactionViewModel = viewModel(
        factory = TransactionViewModelFactory(database)
    )

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("addTransactionScreen") {
            AddTransactionScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = "details/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { backStackEntry ->
            TransactionDetailScreen(
                transactionId = backStackEntry.arguments?.getInt("transactionId") ?: -1,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("expenseDetailScreen") {
            ExpenseDetailScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("incomeDetailScreen") {
            IncomeDetailScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}