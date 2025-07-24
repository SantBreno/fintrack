package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.R
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.components.CategorySelector
import com.devsant.fintrack.ui.components.CurvedTopBackground
import com.devsant.fintrack.ui.components.FilteredTransactionList
import com.devsant.fintrack.ui.components.FloatingNavBar
import com.devsant.fintrack.ui.theme.AppColors
import com.devsant.fintrack.ui.theme.FintrackTheme
import com.devsant.fintrack.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: TransactionViewModel
) {

    val transactionList by viewModel.transactionList.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var balance by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            balance = viewModel.totalBalance()
        }
    }

    HomeScreenContent(
        transactionList = transactionList,
        balance = balance,
        onTransactionClick = { transaction ->
            navController.navigate("details/${transaction.id}")
        },
        onAddClick = { navController.navigate("addTransactionScreen") },
        modifier = modifier,
        navController = navController
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    transactionList: List<Transaction>,
    balance: Double,
    navController: NavHostController,
    onTransactionClick: (Transaction) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf(
        "All", "Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("FinTrack", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape,
                containerColor = Color.White){
                Image(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                FloatingNavBar(navController = navController)
            }
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
                    color = AppColors.Primary
                )

                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                        .fillMaxWidth(0.85f)
                        .height(100.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = AppColors.Secondary),
                    border = BorderStroke(3.dp, color = AppColors.Accent)
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Total Balance: ",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White)
                        Text("R$%.2f".format(balance),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard("Income", modifier = Modifier.weight(1f), navController, "incomeDetailScreen")
                StatCard("Expenses", modifier = Modifier.weight(1f), navController, "expenseDetailScreen")
            }

            Spacer(modifier = Modifier.height(5.dp))

            CategorySelector(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it}
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Recent Transactions",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp
                )

                FilteredTransactionList(
                    transactions = transactionList,
                    selectedCategory = selectedCategory,
                    navController = navController
                )
            }
        }
    }
}



@Composable
fun StatCard(title: String, modifier: Modifier = Modifier,navController: NavHostController, type: String) {
    Card(
        modifier = modifier
            .clickable { navController.navigate(type) }
            .height(40.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Secondary),
        border = BorderStroke(3.dp, color = AppColors.Accent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    val mockTransactions = listOf(
        Transaction(1, "Salary", "01/07/2023", 20000.0, "Income", "Income"),
        Transaction(2, "Rent", "05/07/2023", 5000.0, "Housing", "Expense"),
        Transaction(3, "Groceries", "10/07/2023", 2500.0, "Food", "Expense")
    )

    FintrackTheme {
        HomeScreenContent(
            transactionList = mockTransactions,
            balance = 12500.0,
            navController = rememberNavController(),
            onTransactionClick = {},
            onAddClick = {}
        )
    }
}



