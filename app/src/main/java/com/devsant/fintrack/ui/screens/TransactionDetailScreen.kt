package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.components.DateInputField
import com.devsant.fintrack.ui.components.DatePickerField
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

    var showDatePicker by remember { mutableStateOf(false) }
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
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .height(120.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B213F)),
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    ){
                        Text(
                            text = transaction.title,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = (if (transaction.type == "Expense") "- " else "+ ") + "R$ ${transaction.amount}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 30.sp),
                            color = if (transaction.type == "Expense") Color.Red else Color(0xFF56B25C)
                        )
                        Text(
                            text = transaction.category,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ){
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    color = if(transaction.type == "Income") Color(0xFF56B25C) else Color.Red,
                                    shape = CircleShape
                                )
                                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = transaction.category.firstOrNull()?.uppercase() ?: "?",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = transaction.date,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray
                        )
                    }

                }

            }


            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    TextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    DateInputField(
                        value = date.value,
                        onClick = { showDatePicker = true }
                    )

                    DatePickerField(
                        showDialog = showDatePicker,
                        onDismiss = { showDatePicker = false },
                        onDateSelected = {
                            date.value = it
                            showDatePicker = false
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier,
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    TextField(
                        value = amount.value,
                        onValueChange = { amount.value = it },
                        label = { Text("Amount") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Card(
                        modifier = Modifier,
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) {
                        TextField(
                            value = category.value,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Category") },
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
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
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = { typeExpanded = !typeExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier,
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) {
                        TextField(
                            value = type.value,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Type") },
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
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

                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = onSave, elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp)
                    ) { Text("Save") }
                    Button(onClick = onDelete) { Text("Delete") }
                }
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


