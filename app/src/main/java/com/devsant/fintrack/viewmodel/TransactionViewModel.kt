package com.devsant.fintrack.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsant.fintrack.model.Transaction
import kotlinx.coroutines.launch

open class TransactionViewModel : ViewModel() {
    val transactionList = mutableListOf<Transaction>()
    var title = mutableStateOf("")
    var date = mutableStateOf("")
    var amount = mutableStateOf("")
    var category = mutableStateOf("")
    var type = mutableStateOf("")

    fun addTransaction () {
        viewModelScope.launch {
            val transaction = Transaction(
                id = 0,
                title = title.value,
                date = date.value,
                amount = amount.value,
                category = category.value,
                type = type.value
            )
            transactionList.add(transaction)
        }
    }
    fun updateTransaction(
        id: Int,
        title: String,
        date: String,
        amount: String,
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

    open fun getTransactionById(id: Int): Transaction? {
        return transactionList.find { it.id == id }
    }
}