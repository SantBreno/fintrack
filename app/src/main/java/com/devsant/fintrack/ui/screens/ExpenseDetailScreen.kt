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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.components.CategorySelector
import com.devsant.fintrack.ui.components.CurvedTopBackground
import com.devsant.fintrack.ui.components.FilteredTransactionList
import com.devsant.fintrack.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch

@Composable
fun ExpenseDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: TransactionViewModel
) {
    val transactionList by viewModel.transactionList.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var totalExpense by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            totalExpense = viewModel.totalExpense()
        }
    }

    ExpenseScreenContent(
        transactionList = transactionList,
        totalExpense = totalExpense,
        navController = navController,
        onTransactionClick = { transaction ->
            navController.navigate("details/${transaction.id}")
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreenContent(
    transactionList: List<Transaction>,
    totalExpense: Double,
    navController: NavHostController,
    onTransactionClick: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf(
        "All", "Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping", "Other"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Expense Details", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF6B5B)
                )
            )
        }
    ) { innerPadding ->
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
                    color = Color(0xFFFF6B5B)
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
                    border = BorderStroke(3.dp, color = Color(0xFFFF6B5B))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "R$%.2f".format(totalExpense),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFFF6B5B)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(5.dp))

            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it},
                borderColor = Color(0xFFFF6B5B)
            )

            FilteredTransactionList(
                modifier = Modifier.padding(horizontal = 16.dp),
                transactions = transactionList,
                typeFilter = "Expense",
                selectedCategory = selectedCategory,
                navController = navController
            )
        }

    }
}



