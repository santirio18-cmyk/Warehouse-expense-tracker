
package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.data.ExpenseEntity
import com.example.myapplication.viewmodel.ExpenseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Represents the different categories of expenses
enum class ExpenseCategory(val displayName: String) {
    Pooja("Pooja"),
    Tea("Tea"),
    Petrol("Petrol"),
    Lunch("Lunch"),
    Travel("Travel"),
    Freight("Freight"),
    Maintenance("Maintenance"),
    Utilities("Utilities"),
    Supplies("Supplies"),
    Other("Other")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(
    modifier: Modifier = Modifier, 
    viewModel: ExpenseViewModel? = null,
    onAddExpense: (ExpenseEntity) -> Unit = {}
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(ExpenseCategory.Tea) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add New Expense",
            style = MaterialTheme.typography.headlineMedium
        )
        
        // Date and Time Selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = selectedDate.format(dateFormatter),
                onValueChange = {},
                label = { Text("Date") },
                readOnly = true,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    TextButton(onClick = { showDatePicker = true }) {
                        Text("Select")
                    }
                }
            )
            
            OutlinedTextField(
                value = selectedTime.format(timeFormatter),
                onValueChange = {},
                label = { Text("Time") },
                readOnly = true,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    TextButton(onClick = { showTimePicker = true }) {
                        Text("Select")
                    }
                }
            )
        }

        // Category Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = selectedCategory.displayName,
                onValueChange = {},
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                ExpenseCategory.values().forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.displayName) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        // Amount Field
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Submit Button
        Button(
            onClick = {
                val expenseAmount = amount.toDoubleOrNull()
                if (expenseAmount != null) {
                    val expense = ExpenseEntity(
                        category = selectedCategory.displayName,
                        amount = expenseAmount,
                        description = description,
                        date = LocalDateTime.of(selectedDate, selectedTime)
                    )
                    
                    if (viewModel != null) {
                        viewModel.addExpense(expense)
                    } else {
                        onAddExpense(expense)
                    }
                    
                    // Clear fields
                    amount = ""
                    description = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = amount.toDoubleOrNull() != null
        ) {
            Text("Add Expense")
        }
        
        // Date Picker Dialog
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
            )
            AlertDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                selectedDate = java.time.Instant.ofEpochMilli(it)
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Select Date") },
                text = {
                    DatePicker(state = datePickerState)
                }
            )
        }
        
        // Time Picker Dialog
        if (showTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = selectedTime.hour,
                initialMinute = selectedTime.minute
            )
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            showTimePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Select Time") },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseEntryScreenPreview() {
    MyApplicationTheme {
        ExpenseEntryScreen()
    }
}
