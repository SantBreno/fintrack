package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.ui.components.CategorySelector
import com.devsant.fintrack.ui.components.TransactionCard
import com.devsant.fintrack.viewmodel.TransactionViewModel
import com.devsant.fintrack.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailScreen(
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
                    Text("Expense Details")
                }
            )
        }
    ) { innerPadding ->
        val totalExpense = transactionViewModel.totalExpense()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(3.dp, color = Color.Red)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("R$%.2f".format(totalExpense),
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Red)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it}
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                val filteredTransactions = transactionList.filter {
                    it.type == "Expense" &&
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
fun ExpenseDetailScreenPreview() {
    val mockTransactionList = mutableListOf(
        Transaction(id = 1, title = "Grocery", type = "Expense", amount = 1500.00, category = "Food", date = "2023-09-15"),
        Transaction(id = 2, title = "Internet Bill", type = "Expense", amount = 100.00, category = "Utilities", date = "2023-09-05")
    )

    val mockViewModel = object : TransactionViewModel() {
        override val transactionList = mockTransactionList
    }

    ExpenseDetailScreen(
        navController = rememberNavController(),
        transactionViewModel = mockViewModel
    )
}