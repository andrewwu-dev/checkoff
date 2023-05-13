package com.andrew.checkoff.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.andrew.checkoff.R

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
    Column {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            maxLines = maxLines,
            placeholder = { Text(text = placeholder) },
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
        Text(
            text = "${text.length} / $maxLimit",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
        )
    }
}