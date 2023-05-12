package com.andrew.checkoff.feature.todo.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.andrew.checkoff.core.model.TaskItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipableTask(
    item: TaskItem,
    onSwipe: (TaskItem) -> Unit,
    onTaskPressed: (TaskItem) -> Unit,
    onCheckBoxPressed: (TaskItem) -> Unit,
) {
    val dismissState = rememberDismissState {
        if (it == DismissValue.DismissedToStart) {
            onSwipe(item)
        }
        true
    }
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            DeleteBox(dismissState = dismissState, direction = direction)
        },
    ) {
        TaskCard(
            modifier = Modifier.clickable { onTaskPressed(item) },
            task = item,
            onCheckBoxPressed = onCheckBoxPressed,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteBox(
    dismissState: DismissState,
    direction: DismissDirection
) {
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
}