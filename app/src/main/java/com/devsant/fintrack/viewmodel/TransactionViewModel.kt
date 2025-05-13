package com.devsant.fintrack.viewmodel

import androidx.lifecycle.ViewModel
import com.devsant.fintrack.model.Transaction

open class TransactionViewModel : ViewModel() {
    private var nextId = 1
    val transactionList = mutableListOf<Transaction>()

    fun addTransaction (transaction: Transaction) {
        transactionList.add(transaction.copy(id = nextId++))
    }

    fun getTransactionById(id: Int): Transaction? {
        return transactionList.find { it.id == id }
    }
}