package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId : Int,
    navController: NavHostController,
    viewModel: TransactionViewModel = viewModel()
) {
    val transaction = viewModel.getTransactionById(transactionId)


    transaction?.let {
        viewModel.title.value = it.title
        viewModel.date.value = it.date
        viewModel.amount.value = it.amount
        viewModel.category.value = it.category
        viewModel.type.value = it.type



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
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    value = viewModel.title.value,
                    onValueChange = { viewModel.title.value = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    )
                )
                OutlinedTextField(
                    value = viewModel.date.value,
                    onValueChange = { viewModel.date.value = it },
                    label = { Text("Date") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    )
                )
                OutlinedTextField(
                    value = viewModel.amount.value,
                    onValueChange = { viewModel.amount.value = it },
                    label = { Text("Amount") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    )
                )
                OutlinedTextField(
                    value = viewModel.category.value,
                    onValueChange = { viewModel.category.value = it },
                    label = { Text("Category") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    )
                )
                OutlinedTextField(
                    value = viewModel.type.value,
                    onValueChange = { viewModel.type.value = it },
                    label = { Text("Type") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = {
                                viewModel.updateTransaction(
                                    id = transaction.id,
                                    title = viewModel.title.value,
                                    date = viewModel.date.value,
                                    amount = viewModel.amount.value,
                                    category = viewModel.category.value,
                                    type = viewModel.type.value
                                )
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B213F))
                        ) { Text("Save") }

                        Button(
                            onClick = {
                                viewModel.deleteTransaction(transaction.id)
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) { Text("Delete") }
                    }
                }

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun TransactionDetailScreenPreview() {
    // Fake transaction for preview
    val fakeTransaction = Transaction(
        id = 1,
        title = "Groceries",
        amount = "150.0",
        date = "19/05/2025",
        category = "Food",
        type = "Expense"
    )

    // Fake ViewModel
    val fakeViewModel = object : TransactionViewModel() {
        override fun getTransactionById(id: Int): Transaction? {
            return fakeTransaction
        }
    }

    // Fake NavController
    val fakeNavController = rememberNavController()

    TransactionDetailScreen(
        transactionId = 1,
        navController = fakeNavController,
        viewModel = fakeViewModel
    )
}