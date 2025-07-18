package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId: Int,
    navController: NavHostController,
    viewModel: TransactionViewModel
) {
    val transaction by produceState<Transaction?>(initialValue = null) {
        viewModel.getTransactionById(transactionId)?.let { value = it }
    }

    transaction?.let { trans ->
        viewModel.title.value = trans.title
        viewModel.date.value = trans.date
        viewModel.amount.value = trans.amount.toString()
        viewModel.category.value = trans.category
        viewModel.type.value = trans.type

        TransactionDetailScreenContent(
            transaction = trans,
            navController = navController,
            title = viewModel.title,
            date = viewModel.date,
            amount = viewModel.amount,
            category = viewModel.category,
            type = viewModel.type,
            onSave = {
                viewModel.updateTransaction(
                    id = trans.id,
                    title = viewModel.title.value,
                    date = viewModel.date.value,
                    amount = viewModel.amount.value.toDoubleOrNull() ?: 0.0,
                    category = viewModel.category.value,
                    type = viewModel.type.value
                )
                navController.popBackStack()
            },
            onDelete = {
                viewModel.deleteTransaction(trans.id)
                navController.popBackStack()
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreenContent(
    transaction: Transaction,
    navController: NavHostController,
    title: MutableState<String>,
    date: MutableState<String>,
    amount: MutableState<String>,
    category: MutableState<String>,
    type: MutableState<String>,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    val categoryOptions = listOf("Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping", "Other")
    val typeOptions = listOf("Expense", "Income")

    var categoryExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Transaction Details", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B213F))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = date.value,
                onValueChange = { date.value = it },
                label = { Text("Date") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it },
                label = { Text("Amount") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value = category.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categoryOptions.forEach { selection ->
                        DropdownMenuItem(
                            text = { Text(selection) },
                            onClick = {
                                category.value = selection
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = !typeExpanded }
            ) {
                OutlinedTextField(
                    value = type.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Type") },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = { typeExpanded = false }
                ) {
                    typeOptions.forEach { selection ->
                        DropdownMenuItem(
                            text = { Text(selection) },
                            onClick = {
                                type.value = selection
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onSave) { Text("Save") }
                Button(onClick = onDelete) { Text("Delete") }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TransactionDetailScreenPreview() {
    val transaction = Transaction(
        id = 1,
        title = "Groceries",
        amount = 150.00,
        date = "19/05/2025",
        category = "Food",
        type = "Expense"
    )

    val title = remember { mutableStateOf(transaction.title) }
    val date = remember { mutableStateOf(transaction.date) }
    val amount = remember { mutableStateOf(transaction.amount.toString()) }
    val category = remember { mutableStateOf(transaction.category) }
    val type = remember { mutableStateOf(transaction.type) }
    val navController = rememberNavController()

    TransactionDetailScreenContent(
        transaction = transaction,
        navController = navController,
        title = title,
        date = date,
        amount = amount,
        category = category,
        type = type,
        onSave = {},
        onDelete = {}
    )
}


