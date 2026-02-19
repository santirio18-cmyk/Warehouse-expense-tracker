package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.ExpenseDatabase
import com.example.myapplication.navigation.ExpenseNavigation
import com.example.myapplication.repository.ExpenseRepository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ExpenseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseApp()
                }
            }
        }
    }
}

@Composable
fun ExpenseApp() {
    val database = ExpenseDatabase.getDatabase(androidx.compose.ui.platform.LocalContext.current)
    val repository = ExpenseRepository(database.expenseDao())
    val viewModel: ExpenseViewModel = viewModel { ExpenseViewModel(repository) }
    
    ExpenseNavigation(viewModel = viewModel)
}