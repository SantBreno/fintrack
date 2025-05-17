package com.devsant.fintrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.devsant.fintrack.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId : Int,
    navController: NavHostController,
    viewModel: TransactionViewModel = viewModel()
) {
    val transaction = viewModel.getTransactionById(transactionId)

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
        transaction?.let {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Title: ${it.title}", fontWeight = FontWeight.Bold)
                Text("Date: ${it.date}")
                Text("Amount: ${it.amount}")
                Text("Category: ${it.category}")
                Text("Type: ${it.type}")

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                    Button(onClick = { /*EDIT LOGIC*/ }) {
                        Text("Edit")
                    }
                    Button(onClick = { /*DELETE LOGIC*/ }) {
                        Text("Delete")
                    }
                }
            }
        }

    }
}