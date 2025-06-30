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
import com.devsant.fintrack.ui.components.FilteredTransactionList
import com.devsant.fintrack.viewmodel.TransactionViewModel

@Composable
fun IncomeDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    transactionViewModel: TransactionViewModel
) {
    val transactionList = transactionViewModel.transactionList

    IncomeScreenContent(
        transactionList = transactionList,
        transactionViewModel = transactionViewModel,
        onTransactionClick = { transaction ->
            navController.navigate("details/${transaction.id}")
        },
        modifier = modifier,
        navController = navController
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeScreenContent(
    transactionList: List<Transaction>,
    transactionViewModel: TransactionViewModel,
    navController: NavHostController,
    onTransactionClick: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf(
        "All", "Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping", "Other")

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

            FilteredTransactionList(
                modifier = Modifier.padding(horizontal = 16.dp),
                transactions = transactionList,
                typeFilter = "Income",
                selectedCategory = selectedCategory,
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeScreenContentPreview() {
    val mockTransactions = listOf(
        Transaction(id = 1, title = "Mercado", type = "Income", amount = 1500.00, category = "Food", date = "2023-09-15")
    )

    val mockViewModel = object : TransactionViewModel() {
        override fun totalIncome(): Double {
            return transactionList.filter { it.type == "Income"}.sumOf {it.amount}
        }
    }

    IncomeScreenContent(
        transactionList = mockTransactions,
        transactionViewModel = mockViewModel,
        navController = rememberNavController(),
        onTransactionClick = {}
    )
}
