package com.example.myapplication.repository

import com.example.myapplication.data.ExpenseDao
import com.example.myapplication.data.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun getAllExpenses(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()

    fun getExpensesByDate(date: LocalDate): Flow<List<ExpenseEntity>> = 
        expenseDao.getExpensesByDate(date)

    fun getExpensesByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<ExpenseEntity>> = 
        expenseDao.getExpensesByDateRange(startDate, endDate)

    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>> = 
        expenseDao.getExpensesByCategory(category)

    suspend fun getTotalExpensesByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Double? = 
        expenseDao.getTotalExpensesByDateRange(startDate, endDate)

    suspend fun getTotalExpensesByCategoryAndDateRange(category: String, startDate: LocalDateTime, endDate: LocalDateTime): Double? = 
        expenseDao.getTotalExpensesByCategoryAndDateRange(category, startDate, endDate)

    suspend fun insertExpense(expense: ExpenseEntity): Long = 
        expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: ExpenseEntity) = 
        expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: ExpenseEntity) = 
        expenseDao.deleteExpense(expense)

    suspend fun deleteExpenseById(id: Long) = 
        expenseDao.deleteExpenseById(id)
}




