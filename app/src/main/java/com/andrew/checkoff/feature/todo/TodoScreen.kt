package com.andrew.checkoff.feature.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrew.checkoff.R
import com.andrew.checkoff.core.nav.UiEvent
import com.andrew.checkoff.core.theme.CheckoffTheme
import com.andrew.checkoff.feature.todo.ui.SwipableTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun TodoScreen(
    onNavigateEvent: (UiEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigateEvent(event)
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionMsg
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUndoDeletePressed()
                    }
                }

                else -> Unit
            }
        }
    }
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        floatingActionButton = { AddButton { viewModel.onAddTaskPressed() } }
    ) { contentPadding ->
        if (viewState.value.isLoading) {
            LoadingSpinner(
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
        } else if (viewState.value.tasks.isEmpty()) {
            TrophyDrawing()
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
fun LoadingSpinner(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun TrophyDrawing() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.trophy),
            contentDescription = "trophy",
            modifier = Modifier.fillMaxSize(0.4f)
        )
        Text(
            text = stringResource(R.string.congratulations_youre_all_done), style = TextStyle(
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        )
    }
}

@Composable
fun TodayHeader() {
    Text(
        text = stringResource(R.string.today), color = Color.Black, style = TextStyle(
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
            onNavigateEvent = {}
        )
    }
}