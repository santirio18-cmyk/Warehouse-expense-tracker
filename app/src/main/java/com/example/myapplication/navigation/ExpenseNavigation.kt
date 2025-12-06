package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ExpenseEntryScreen
import com.example.myapplication.ExpenseListScreen
import com.example.myapplication.ExpenseSummaryScreen
import com.example.myapplication.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseNavigation(
    modifier: Modifier = Modifier,
    viewModel: ExpenseViewModel
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                listOf(
                    Screen.AddExpense to Icons.Default.Add,
                    Screen.ExpenseList to Icons.Default.List,
                    Screen.Summary to Icons.Default.Assessment
                ).forEach { (screen, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.AddExpense.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.AddExpense.route) {
                ExpenseEntryScreen(
                    viewModel = viewModel
                )
            }
            
            composable(Screen.ExpenseList.route) {
                ExpenseListScreen(
                    viewModel = viewModel,
                    onNavigateToAdd = {
                        navController.navigate(Screen.AddExpense.route)
                    }
                )
            }
            
            composable(Screen.Summary.route) {
                ExpenseSummaryScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

sealed class Screen(val route: String, val title: String) {
    object AddExpense : Screen("add_expense", "Add")
    object ExpenseList : Screen("expense_list", "List")
    object Summary : Screen("summary", "Summary")
}




