package com.andrew.checkoff.feature.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrew.checkoff.ui.theme.CheckoffTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 50.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(items = viewState.value.tasks) { _, item ->
            TaskCard(title = item.title, desc = item.desc)
        }
    }
}

@Preview("Todo Screen", showSystemUi = true)
@Composable
private fun PreviewTodoScreen() {
    CheckoffTheme {
        TodoScreen()
    }
}