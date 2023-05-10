package com.andrew.checkoff

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.andrew.checkoff.feature.todo.TodoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainApp() {
    TodoScreen()
}