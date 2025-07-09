package com.devsant.fintrack.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsant.fintrack.data.AppDatabase
import com.devsant.fintrack.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(private val database: AppDatabase) : ViewModel() {
    private val _transactionList = MutableStateFlow<List<Transaction>>(emptyList())
    val transactionList = _transactionList.asStateFlow()

    var title = mutableStateOf("")
    var date = mutableStateOf("")
    var amount = mutableStateOf("")
    var category = mutableStateOf("")
    var type = mutableStateOf("")

    init {
        viewModelScope.launch {
            database.transactionDao().getAllTransactions().collect { transactions ->
                _transactionList.value = transactions
            }
        }
    }

    fun addTransaction() {
        viewModelScope.launch {
            val transaction = Transaction(
                title = title.value,
                date = date.value,
                amount = amount.value.toDoubleOrNull() ?: 0.0,
                category = category.value,
                type = type.value
            )
            database.transactionDao().insertTransaction(transaction)
            // Reset fields after adding
            title.value = ""
            date.value = ""
            amount.value = ""
            category.value = ""
            type.value = ""
        }
    }

    fun updateTransaction(
        id: Int,
        title: String,
        date: String,
        amount: Double,
        category: String,
        type: String
    ) {
        viewModelScope.launch {
            val transaction = Transaction(id, title, date, amount, category, type)
            database.transactionDao().updateTransaction(transaction)
        }
    }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            val transaction = _transactionList.value.find { it.id == id }
            transaction?.let {
                database.transactionDao().deleteTransaction(it)
            }
        }
    }

    suspend fun getTransactionById(id: Int): Transaction? {
        return database.transactionDao().getTransactionById(id)
    }

    suspend fun totalIncome(): Double {
        return database.transactionDao().getTotalIncome()
    }

    suspend fun totalExpense(): Double {
        return database.transactionDao().getTotalExpense()
    }

    suspend fun totalBalance(): Double {
        return totalIncome() - totalExpense()
    }
}