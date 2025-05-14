package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.viewmodel.TransactionViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    transactionViewModel: TransactionViewModel) {

    val transactionList = transactionViewModel.transactionList

    HomeScreenContent(
        transactionList = transactionList,
        onTransactionClick = { transaction ->
            navController.navigate("details/${transaction.id}")
        },
        onAddClick = { navController.navigate("addTransactionScreen") },
        modifier = modifier
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    transactionList: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("FinTrack", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Total Balance: ", style = MaterialTheme.typography.bodyLarge)
                    Text("$1000.00", style = MaterialTheme.typography.headlineLarge)
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard("Income", "R$1000.00", modifier = Modifier.weight(1f))
                StatCard("Expense", "R$500.00", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Recent Transactions", style = MaterialTheme.typography.headlineMedium)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                items(transactionList.size) { transaction ->
                    TransactionCard(
                        transaction = transactionList[transaction],
                        onClick = { onTransactionClick(transactionList[transaction]) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, amount: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(150.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(amount, style = MaterialTheme.typography.headlineLarge, fontSize = 20.sp)
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction,
                    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = {  }),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if(transaction.type == "Income") Color(0xFFDFF5DC) else Color(0xFFFFE0E0),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.category.firstOrNull()?.uppercase() ?: "?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

            Spacer(modifier =  Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = transaction.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Text(
                text = (if (transaction.type == "Expense") "- " else "+ ") + "R$ ${transaction.amount}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = if (transaction.type == "Expense") Color.Red else Color(0xFF56B25C)
            )
        }

    }
}




@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    val sampleTransactions = listOf(
        Transaction(id = 1, title = "Grocery", type = "Expense", amount = "1500", category = "Food", date = "2023-09-15"),
        Transaction(id = 2, title = "Salary", type = "Income", amount = "25000", category = "Salary", date = "2023-09-10"),
        Transaction(id = 3, title = "Internet Bill", type = "Expense", amount = "100", category = "Utilities", date = "2023-09-05")
    )

    HomeScreenContent(
        transactionList = sampleTransactions,
        onTransactionClick = {},
        onAddClick = {}
    )
}
