package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.devsant.fintrack.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    modifier : Modifier = Modifier,
    navController: NavHostController,
    viewModel: TransactionViewModel = viewModel()
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("FinTrack", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Transaction", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = viewModel.title.value,
            onValueChange = { viewModel.title.value = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = viewModel.date.value,
            onValueChange = { viewModel.date.value = it },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = viewModel.amount.value,
            onValueChange = { viewModel.amount.value = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = viewModel.category.value,
            onValueChange = { viewModel.category.value = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = viewModel.type.value,
            onValueChange = { viewModel.type.value = it },
            label = { Text("Type") },
            modifier = Modifier.fillMaxWidth())

        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
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
                    modifier = Modifier.weight(1f)) {
                    Text("AddTransaction")
                }
                Button(onClick = { navController.navigate("home") }, modifier = Modifier.weight(1f)) {
                    Text("Back to Home")
                }
            }

        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun AddTransactionScreenPreview() {
    val navController = rememberNavController()
    AddTransactionScreen(navController = navController)
}*/
