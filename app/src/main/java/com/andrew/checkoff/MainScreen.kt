package com.andrew.checkoff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andrew.checkoff.feature.todo.TodoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainTopBar() {
    TopAppBar(
        title = { Text(text = "Today") },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainApp() {
    Scaffold(
        topBar = { MainTopBar() },
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiaryContainer),
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            TodoScreen()
        }
    }
}