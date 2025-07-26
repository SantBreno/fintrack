package com.devsant.fintrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.model.Transaction
import com.devsant.fintrack.ui.theme.AppColors
import com.devsant.fintrack.ui.theme.FintrackTheme

@Composable
fun TransactionCard(
    transaction: Transaction,
    navController: NavHostController,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {navController.navigate("details/${transaction.id}") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                    text = transaction.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = (if (transaction.type == "Expense") "- " else "+ ") + "R$ ${transaction.amount}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (transaction.type == "Expense") AppColors.ExpenseRed else AppColors.IncomeGreen
                )

                Text(
                    text = transaction.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun TransactionCardPreview() {
    val sampleTransaction = Transaction(
        id = 1,
        title = "Grocery Shopping",
        amount = 125.50,
        date = "2023-05-15",
        type = "Expense",
        category = "Food"
    )

    val navController = rememberNavController()

    FintrackTheme {
        TransactionCard(
            transaction = sampleTransaction,
            navController = navController
        )
    }
}