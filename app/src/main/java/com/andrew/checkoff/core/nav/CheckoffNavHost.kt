package com.andrew.checkoff.core.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andrew.checkoff.feature.add_task.AddTaskScreen
import com.andrew.checkoff.feature.todo.TodoScreen

@Composable
fun CheckoffNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.TODO
    ) {
        composable(Route.TODO) {
            TodoScreen(
                onAddPressed = { navController.navigate(Route.ADD_TASK) }
            )
        }
        composable(Route.ADD_TASK) {
            AddTaskScreen(
                onBackPressed = navController::popBackStack,
            )
        }
    }
}