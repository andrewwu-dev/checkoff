package com.andrew.checkoff.feature.todo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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


@Composable
internal fun MainTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Today", color = Color.Black, style = TextStyle(
                    fontSize = MaterialTheme.typography.h4.fontSize
                )
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    )
}


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
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = viewModel::onAddTaskPressed,
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    tint = Color.White,
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
            item {
                Text(
                    text = "Today", color = Color.Black, style = TextStyle(
                        fontSize = MaterialTheme.typography.h4.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
                )
            }
            items(items = viewState.value.tasks, { it.id ?: 0 }) { item ->
                val dismissState = rememberDismissState {
                    if (it == DismissValue.DismissedToStart) {
                        viewModel.onTaskSwiped(item)
                    }
                    true
                }
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colors.background
                                DismissValue.DismissedToEnd -> MaterialTheme.colors.error
                                DismissValue.DismissedToStart -> MaterialTheme.colors.error
                            }
                        )
                        val alignment = when (direction) {
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                        }
                        val icon = Icons.Default.Delete
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Trashcan",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                ) {
                    TaskCard(
                        modifier = Modifier.clickable { viewModel.onTaskPressed(item) },
                        task = item,
                        onCheckBoxPressed = viewModel::onCheckBoxPressed
                    )
                }
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