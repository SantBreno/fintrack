package com.devsant.fintrack.model

data class Transaction(
    val id: Int,
    val title: String,
    /*val date: String,*/
    val amount: String,
    /*val category: String,*/
    val type: String
)