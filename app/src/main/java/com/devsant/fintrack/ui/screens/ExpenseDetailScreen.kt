package com.devsant.fintrack.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.ui.components.CategorySelector

@Composable
fun ExpenseDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,

){
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf(
        "All", "Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping", "Other")

    CategorySelector(
        categories = categories,
        selectedCategory = selectedCategory,
        onCategorySelected = { selectedCategory = it}
    )

}

@Preview(showBackground = true)
@Composable
fun ExpenseDetailScreenPreview() {
    val navController = rememberNavController()

    ExpenseDetailScreen(
        navController = navController
    )
}