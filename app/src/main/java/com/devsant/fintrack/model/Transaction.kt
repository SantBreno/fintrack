package com.devsant.fintrack.model

data class Transaction(
    val id: Int,
    val date: String,
    val description: String,
    val amount: Double,
    val category: String,
    val type: String
)