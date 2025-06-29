package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.R
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.components.CategorySelector
import com.devsant.fintrack.ui.components.SearchBar
import com.devsant.fintrack.ui.components.TransactionCard
import com.devsant.fintrack.viewmodel.TransactionViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    transactionViewModel: TransactionViewModel
) {

    val transactionList = transactionViewModel.transactionList

    HomeScreenContent(
        transactionList = transactionList,
        transactionViewModel = transactionViewModel,
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
    transactionViewModel: TransactionViewModel,
    navController: NavHostController,
    onTransactionClick: (Transaction) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
){
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
                    containerColor = Color(0xFF1B213F)
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
        }
    ) { innerPadding ->
        val balance = transactionViewModel.totalBalance()
        var searchQueryFieldValue by remember { mutableStateOf(TextFieldValue("")) }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(25.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF003D5F)),
                border = BorderStroke(3.dp, color = Color(0XFFE83A44))
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

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard("Income", modifier = Modifier.weight(1f), navController, "incomeDetailScreen")
                StatCard("Expenses", modifier = Modifier.weight(1f), navController, "expenseDetailScreen")
            }

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
                SearchBar(
                    hint = "Search...",
                    searchQuery = searchQueryFieldValue,
                    onSearchQueryChange = { searchQueryFieldValue = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Recent Transactions",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val filteredTransactions = transactionList.filter {
                        (selectedCategory.isEmpty() || it.category == selectedCategory) &&
                                (searchQueryFieldValue.text.isBlank() ||
                                        it.title.contains(searchQueryFieldValue.text, ignoreCase = true))

                    }

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
}



@Composable
fun StatCard(title: String, modifier: Modifier = Modifier,navController: NavHostController, type: String) {
    Card(
        modifier = modifier
            .clickable { navController.navigate(type) }
            .height(40.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF003D5F)),
        border = BorderStroke(3.dp, color = Color(0XFFEE7779))
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
        Transaction(id = 1, title = "Grocery", type = "Expense", amount = 1500.00, category = "Food", date = "2023-09-15"),
        Transaction(id = 2, title = "Salary", type = "Income", amount = 25000.00, category = "Salary", date = "2023-09-10"),
        Transaction(id = 3, title = "Internet Bill", type = "Expense", amount = 100.00, category = "Utilities", date = "2023-09-05")
    )

    // Dummy ViewModel with mocked balance logic
    val mockViewModel = object : TransactionViewModel() {

        override fun totalBalance(): Double {
            return totalIncome() - totalExpense()
        }
    }

    HomeScreenContent(
        transactionList = mockTransactions,
        transactionViewModel = mockViewModel,
        onTransactionClick = {},
        onAddClick = {},
        navController = rememberNavController()
    )
}
