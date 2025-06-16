package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.components.CategorySelector
import com.devsant.fintrack.ui.components.TransactionCard
import com.devsant.fintrack.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeDetailScreen(
   modifier: Modifier = Modifier,
   navController: NavHostController,
   transactionViewModel: TransactionViewModel
){

    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf(
        "All", "Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping", "Other")
    val transactionList = transactionViewModel.transactionList

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Income Details")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it}
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ){
                val filteredTransactions = transactionList.filter {
                    it.type == "Income" &&
                            (selectedCategory == "All" || selectedCategory.isEmpty() || it.category == selectedCategory)
                }

                items(filteredTransactions.size) { transaction ->
                    TransactionCard(
                        transaction = transactionList[transaction],
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeDetailScreenPreview() {
    val mockTransactionList = mutableListOf(
        Transaction(id = 1, title = "Jorgen S.", type = "Income", amount = 1500.00, category = "Food", date = "2023-09-15"),
        Transaction(id = 2, title = "Salary", type = "Income", amount = 25000.00, category = "Salary", date = "2023-09-10"),
    )

    val mockViewModel = object : TransactionViewModel() {
        override val transactionList = mockTransactionList
    }

    IncomeDetailScreen(
        navController = rememberNavController(),
        transactionViewModel = mockViewModel
    )
}