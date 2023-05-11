package com.andrew.checkoff.core.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andrew.checkoff.feature.add_edit_task.AddTaskScreen
import com.andrew.checkoff.feature.todo.TodoScreen

@Composable
fun CheckoffNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.TODO
    ) {
        composable(
            Route.TODO,
        ) {
            TodoScreen(
                onNaviateEvent = { navController.navigate(it.route) }
            )
        }
        composable(
            Route.ADD_TASK + "?taskId={taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
        ) {
            AddTaskScreen(
                onBackPressed = navController::popBackStack,
            )
        }
    }
}