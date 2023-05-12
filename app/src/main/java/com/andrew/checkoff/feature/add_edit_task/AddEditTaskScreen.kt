package com.andrew.checkoff.feature.add_edit_task

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrew.checkoff.R
import com.andrew.checkoff.core.nav.UiEvent
import com.andrew.checkoff.core.theme.CheckoffTheme
import com.andrew.checkoff.core.ui.MaxLimitTextField

@Composable
internal fun AddTaskTopBar(
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.Black,
                    contentDescription = "backIcon"
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    )
}

@Composable
internal fun AddTaskScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionMsg
                    )
                }

                else -> Unit
            }
        }
    }
    val focusManager = LocalFocusManager.current
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { AddTaskTopBar({ viewModel.onBackPress() }) },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel.onDonePressed()
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    tint = Color.White,
                    contentDescription = "Done"
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            Text(
                text = stringResource(id = R.string.what_would_you_like_to_do),
                color = Color.Black,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h4.fontSize,
                ),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.paddingAppBar)))
                MaxLimitTextField(
                    maxLimit = 48,
                    maxLines = 2,
                    text = viewState.title,
                    placeholder = stringResource(R.string.new_task),
                    focusManager = focusManager,
                    onValueChange = {
                        viewModel.onTitleChanged(it)
                    })
                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))
                MaxLimitTextField(
                    maxLimit = 128,
                    maxLines = 4,
                    text = viewState.desc,
                    placeholder = stringResource(R.string.description),
                    focusManager = focusManager,
                    onValueChange = {
                        viewModel.onDescChanged(it)
                    })
            }
        }
    }
}

@Preview("Add Task Screen")
@Composable
private fun AddTaskScreenPreview() {
    CheckoffTheme {
        AddTaskScreen({})
    }
}