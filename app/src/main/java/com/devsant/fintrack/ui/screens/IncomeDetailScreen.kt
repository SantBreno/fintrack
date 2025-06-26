package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.components.CategorySelector
import com.devsant.fintrack.ui.components.CurvedTopBackground
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
                    Text("Income Details", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF56B25C)
                )
            )
        }
    ) { innerPadding ->
        val totalIncome = transactionViewModel.totalIncome()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                CurvedTopBackground(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    color = Color(0xFF56B25C)
                )

                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                        .fillMaxWidth(0.85f)
                        .height(100.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(3.dp, color = Color(0xFF56B25C))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "R$%.2f".format(totalIncome),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF56B25C)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(5.dp))

            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it},
                borderColor = Color(0xFF56B25C)
            )
            val filteredTransactions = transactionList.filter {
                it.type == "Income" || it.type != "Expense" &&
                        (selectedCategory == "All" || selectedCategory.isEmpty() || it.category == selectedCategory)
            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ){

                items(filteredTransactions, key = { it.id }) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeDetailScreenPreview() {
    val mockViewModel = object : TransactionViewModel() {
        init {
            transactionList.addAll(
                listOf(
                    Transaction(
                        id = 1, title = "Freelance Project", date = "2025-06-15", amount = 1500.0, category = "Work", type = "Income"
                    ),
                    Transaction(
                        id = 2, title = "Gift", date = "2025-06-10", amount = 300.0, category = "Other", type = "Income"
                    ),
                    Transaction(
                        id = 3, title = "Groceries", date = "2025-06-09", amount = 200.0, category = "Food", type = "Income"
                    )
                )
            )
        }

        override fun totalIncome(): Double {
            return transactionList.filter { it.type == "Income" }.sumOf { it.amount }
        }
    }

    IncomeDetailScreen(
        navController = rememberNavController(),
        transactionViewModel = mockViewModel
    )
}
