package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.ExpenseEntity
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ExpenseViewModel
import com.example.myapplication.viewmodel.FilterType
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseSummaryScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseViewModel? = null
) {
    val uiState by viewModel?.uiState?.collectAsState() ?: remember { 
        mutableStateOf(com.example.myapplication.viewmodel.ExpenseUiState()) 
    }
    
    var selectedPeriod by remember { mutableStateOf(FilterType.MONTHLY) }
    var categoryBreakdown by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    val scope = rememberCoroutineScope()
    
    // Calculate category breakdown
    LaunchedEffect(uiState.expenses) {
        categoryBreakdown = uiState.expenses.groupBy { it.category }
            .mapValues { (_, expenses) -> expenses.sumOf { it.amount } }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Expense Summary",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Period Selection
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Select Period",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterType.values().forEach { period ->
                        FilterChip(
                            onClick = { 
                                selectedPeriod = period
                                viewModel?.updateFilterType(period)
                            },
                            label = { Text(period.name) },
                            selected = selectedPeriod == period
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryCard(
                title = "Total Expenses",
                value = "₹${String.format("%.2f", uiState.totalAmount)}",
                modifier = Modifier.weight(1f)
            )
            
            SummaryCard(
                title = "Count",
                value = "${uiState.expenses.size}",
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Category Breakdown
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Category Breakdown",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (categoryBreakdown.isEmpty()) {
                    Text(
                        text = "No data available",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(categoryBreakdown.toList().sortedByDescending { it.second }) { (category, amount) ->
                            CategoryBreakdownItem(
                                category = category,
                                amount = amount,
                                totalAmount = uiState.totalAmount
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Recent Expenses
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Recent Expenses",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (uiState.expenses.isEmpty()) {
                    Text(
                        text = "No expenses found",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(uiState.expenses.take(5)) { expense ->
                            RecentExpenseItem(expense = expense)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun CategoryBreakdownItem(
    category: String,
    amount: Double,
    totalAmount: Double
) {
    val percentage = if (totalAmount > 0) (amount / totalAmount * 100) else 0.0
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${String.format("%.1f", percentage)}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = "₹${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RecentExpenseItem(
    expense: ExpenseEntity
) {
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm")
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = expense.category,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = expense.date.format(dateFormatter),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = "₹${String.format("%.2f", expense.amount)}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseSummaryScreenPreview() {
    MyApplicationTheme {
        ExpenseSummaryScreen()
    }
}




