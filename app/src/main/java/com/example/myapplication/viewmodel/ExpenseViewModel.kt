package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ExpenseEntity
import com.example.myapplication.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class ExpenseUiState(
    val expenses: List<ExpenseEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalAmount: Double = 0.0,
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedCategory: String? = null,
    val filterType: FilterType = FilterType.DAILY
)

enum class FilterType {
    DAILY, WEEKLY, MONTHLY, ALL
}

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val currentState = _uiState.value
                val expenses = when (currentState.filterType) {
                    FilterType.DAILY -> repository.getExpensesByDate(currentState.selectedDate)
                    FilterType.WEEKLY -> {
                        val startOfWeek = currentState.selectedDate.minusDays(currentState.selectedDate.dayOfWeek.value - 1L)
                        val endOfWeek = startOfWeek.plusDays(6)
                        repository.getExpensesByDateRange(
                            startOfWeek.atStartOfDay(),
                            endOfWeek.atTime(23, 59, 59)
                        )
                    }
                    FilterType.MONTHLY -> {
                        val startOfMonth = currentState.selectedDate.withDayOfMonth(1)
                        val endOfMonth = startOfMonth.plusMonths(1).minusDays(1)
                        repository.getExpensesByDateRange(
                            startOfMonth.atStartOfDay(),
                            endOfMonth.atTime(23, 59, 59)
                        )
                    }
                    FilterType.ALL -> repository.getAllExpenses()
                }.first()

                val totalAmount = expenses.sumOf { it.amount }
                _uiState.value = currentState.copy(
                    expenses = expenses,
                    totalAmount = totalAmount,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            try {
                repository.insertExpense(expense)
                loadExpenses()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            try {
                repository.deleteExpense(expense)
                loadExpenses()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
        loadExpenses()
    }

    fun updateFilterType(filterType: FilterType) {
        _uiState.value = _uiState.value.copy(filterType = filterType)
        loadExpenses()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}




