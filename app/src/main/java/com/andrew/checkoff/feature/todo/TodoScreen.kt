package com.andrew.checkoff.feature.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrew.checkoff.R
import com.andrew.checkoff.core.nav.UiEvent
import com.andrew.checkoff.core.theme.CheckoffTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainTopBar() {
    TopAppBar(
        title = { Text(text = "Today") },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TodoScreen(
    onNaviateEvent: (UiEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNaviateEvent(event)
                else -> Unit
            }
        }
    }
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = viewModel::onAddTaskPressed,
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Add"
                )
            }
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                vertical = dimensionResource(R.dimen.padding_large),
                horizontal = dimensionResource(R.dimen.padding_medium)
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = viewState.value.tasks) { _, item ->
                TaskCard(
                    modifier = Modifier.clickable { viewModel.onTaskPressed(item) },
                    task = item,
                    onCheckBoxPressed = viewModel::onCheckBoxPressed
                )
            }
        }
    }
}

@Preview("Todo Screen", showSystemUi = true)
@Composable
private fun PreviewTodoScreen() {
    CheckoffTheme {
        TodoScreen(
            onNaviateEvent = {}
        )
    }
}