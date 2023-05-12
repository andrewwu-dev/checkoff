package com.andrew.checkoff.feature.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrew.checkoff.R
import com.andrew.checkoff.core.nav.UiEvent
import com.andrew.checkoff.core.theme.CheckoffTheme
import com.andrew.checkoff.feature.todo.ui.SwipableTask

@OptIn(ExperimentalMaterialApi::class)
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
        floatingActionButton = { AddButton { viewModel.onAddTaskPressed() } }
    ) { contentPadding ->
        if (viewState.value.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                item { TodayHeader() }
                items(items = viewState.value.tasks, { it.id ?: 0 }) { item ->
                    SwipableTask(
                        item = item,
                        onSwipe = viewModel::onTaskSwiped,
                        onTaskPressed = viewModel::onTaskPressed,
                        onCheckBoxPressed = viewModel::onCheckBoxPressed
                    )
                }
            }
        }
    }
}

@Composable
fun TodayHeader() {
    Text(
        text = "Today", color = Color.Black, style = TextStyle(
            fontSize = MaterialTheme.typography.h4.fontSize
        ),
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
    )
}

@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            tint = Color.White,
            contentDescription = "Add"
        )
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