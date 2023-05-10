package com.andrew.checkoff.feature.add_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrew.checkoff.R
import com.andrew.checkoff.core.theme.CheckoffTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTaskTopBar(
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.addTask)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MaxLimitTextField(
    maxLimit: Int,
    maxLines: Int,
    text: String,
    imeAction: ImeAction = ImeAction.Done,
    placeholder: String,
    focusManager: FocusManager,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        maxLines = maxLines,
        placeholder = { Text(text = placeholder) },
        supportingText = {
            Text(
                text = "${text.length} / $maxLimit",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTaskScreen(
    onBackPressed: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    var titleText by remember { mutableStateOf("") }
    var descText by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AddTaskTopBar(onBackPressed) },
        floatingActionButton = {
            // add a finish button
            FloatingActionButton(onClick = {
                viewModel.onDonePressed()
                onBackPressed()
            }) {
                Text(text = stringResource(id = R.string.done))
            }
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.paddingAppBar)))
            MaxLimitTextField(
                maxLimit = 48,
                maxLines = 2,
                text = titleText,
                imeAction = ImeAction.Next,
                placeholder = stringResource(R.string.title),
                focusManager = focusManager,
                onValueChange = {
                    titleText = it
                    viewState.title = it
                })
            Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium)))
            MaxLimitTextField(
                maxLimit = 128,
                maxLines = 4,
                text = descText,
                placeholder = stringResource(R.string.description),
                focusManager = focusManager,
                onValueChange = {
                    descText = it
                    viewState.title = it
                })
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