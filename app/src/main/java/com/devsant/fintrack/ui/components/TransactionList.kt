package com.devsant.fintrack.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devsant.fintrack.model.Transaction

@Composable
fun FilteredTransactionList(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
    typeFilter: String? = null,
    selectedCategory: String,
    searchQuery: String? = null,
    navController: NavHostController
) {
    val filteredTransactions = transactions.filter {
        val matchesType = typeFilter == null || it.type.equals(typeFilter, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || selectedCategory.isEmpty() || it.category == selectedCategory
        val matchesSearch = searchQuery.isNullOrBlank() || it.title.contains(searchQuery, ignoreCase = true)

        matchesType && matchesCategory && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(filteredTransactions, key = { it.id }) { transaction ->
            TransactionCard(
                transaction = transaction,
                navController = navController
            )
        }
    }
}
