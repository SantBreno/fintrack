package com.devsant.fintrack.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devsant.fintrack.model.Transaction

open class TransactionViewModel : ViewModel() {
    var transactionList = mutableStateListOf<Transaction>()
        private set

    private var nextId = 1

    var title = mutableStateOf("")
    var date = mutableStateOf("")
    var amount = mutableStateOf("")
    var category = mutableStateOf("")
    var type = mutableStateOf("")

    private fun parseAmount(): Double {
        return amount.value.toDoubleOrNull() ?: 0.0
    }

    fun addTransaction() {
        val transaction = Transaction(
            id = nextId++,
            title = title.value,
            date = date.value,
            amount = parseAmount(),
            category = category.value,
            type = type.value
        )
        transactionList.add(transaction)
    }

    fun updateTransaction(
        id: Int,
        title: String,
        date: String,
        amount: Double,
        category: String,
        type: String
    ) {
        val index = transactionList.indexOfFirst { it.id == id }
        if (index != -1) {
            transactionList[index] = transactionList[index].copy(
                title = title,
                date = date,
                amount = amount,
                category = category,
                type = type
            )
        }
    }

    fun deleteTransaction(id: Int) {
        val index = transactionList.indexOfFirst { it.id == id }
        if (index != -1) {
            transactionList.removeAt(index)
        }
    }

    open fun getTransactionById(id: Int): Transaction? {
        return transactionList.find { it.id == id }
    }

    open fun totalIncome(): Double {
        return transactionList.filter { it.type.equals("Income", ignoreCase = true) }
            .sumOf { it.amount }
    }

    open fun totalExpense(): Double {
        return transactionList.filter { it.type.equals("Expense", ignoreCase = true) }
            .sumOf { it.amount }
    }

    open fun totalBalance(): Double {
        return totalIncome() - totalExpense()
    }
}
