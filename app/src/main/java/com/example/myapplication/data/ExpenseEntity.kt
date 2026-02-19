package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val amount: Double,
    val description: String,
    val date: LocalDateTime,
    val createdDate: LocalDateTime = LocalDateTime.now()
)

