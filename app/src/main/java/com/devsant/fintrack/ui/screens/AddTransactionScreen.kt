package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.devsant.fintrack.data.AppDatabase
import com.devsant.fintrack.ui.components.DatePickerField
import com.devsant.fintrack.ui.theme.FintrackTheme
import com.devsant.fintrack.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    modifier : Modifier = Modifier,
    navController: NavHostController,
    viewModel: TransactionViewModel = viewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val categoryOptions = listOf("Food", "Transport", "Entertainment", "Utilities", "Health", "Shopping","Other")
    val typeOptions = listOf("Expense", "Income")

    var categoryExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Transaction", color = Color.White, fontWeight = FontWeight.Bold)
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
        ) {

            Text(
                "Let's create a new Transaction",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B213F)
                ),
                fontSize = 25.sp
            )

            Text(
                "Create a transaction by filling in the details below.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF1B213F)
                ),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.title.value,
            onValueChange = { viewModel.title.value = it },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1B213F),
                unfocusedBorderColor = Color(0xFF1B213F),
            )
        )


            DateInputField(
                value = viewModel.date.value,
                onClick = { showDatePicker = true }
            )


            DatePickerField(
                showDialog = showDatePicker,
                onDismiss = { showDatePicker = false },
                onDateSelected = {
                    viewModel.date.value = it
                    showDatePicker = false
                }
            )


            OutlinedTextField(
                value = viewModel.amount.value,
                onValueChange = { viewModel.amount.value = it },
                label = { Text("Amount") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1B213F),
                    unfocusedBorderColor = Color(0xFF1B213F),
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )

            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = viewModel.category.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = Color(0xFF1B213F),
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
                                viewModel.category.value = selection
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = !typeExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = viewModel.type.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Type") },
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1B213F),
                        unfocusedBorderColor = Color(0xFF1B213F),
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
                                viewModel.type.value = selection
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Button(
                        onClick = {
                            viewModel.addTransaction()
                            navController.navigate("home")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B213F))
                    ) {
                        Text("Add Transaction")
                    }
                }

            }
        }
    }
}

@Composable
fun DateInputField(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date"
) {
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        shape = MaterialTheme.shapes.medium,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker",
                tint = Color(0xFF1B213F)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusProperties { canFocus = false }
            .clickable (
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1B213F),
            unfocusedBorderColor = Color(0xFF1B213F),
            disabledBorderColor = Color(0xFF1B213F),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AddTransactionScreenPreview() {
    val navController = rememberNavController()
    // Create an in-memory database for preview purposes
    val context = LocalContext.current
    val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    FintrackTheme {
        AddTransactionScreen(
            navController = navController,
            viewModel = TransactionViewModel(database)
        )
    }
}
