package com.andrew.checkoff.feature.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrew.checkoff.R
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.theme.CheckoffTheme

@Composable
internal fun TaskCard(
    modifier: Modifier = Modifier,
    task: TaskItem,
    onCheckBoxPressed: (TaskItem) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        ) {
            CircleCheckbox(
                selected = task.completed,
                onChecked = { onCheckBoxPressed(task) },
            )
            Column(Modifier.padding(top = 8.dp)) {
                Text(
                    color = Color.Black,
                    text = task.title,
                    style = MaterialTheme.typography.h5,
                    maxLines = 2
                )
                if (task.desc.isNotEmpty())
                    Text(
                        color = Color.Black,
                        text = task.desc,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2
                    )
            }
        }
    }
}

@Composable
fun CircleCheckbox(selected: Boolean, enabled: Boolean = true, onChecked: () -> Unit) {
    IconButton(
        onClick = { onChecked() },
        enabled = enabled,
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .scale(1.2f)
                    .background(Color.White, shape = CircleShape),
                contentDescription = "Checkmark",
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.baseline_radio_button_unchecked_24),
                tint = Color.Gray,
                modifier = Modifier
                    .scale(1.2f)
                    .background(Color.Transparent, shape = CircleShape),
                contentDescription = "Circle"
            )
        }
    }
}

@Preview("Task")
@Composable
private fun PreviewTask() {
    CheckoffTheme {
        TaskCard(
            task = TaskItem(
                title = "Go fishing",
                desc = "I love fishing.",
            ),
            onCheckBoxPressed = {},
        )
    }
}

@Preview("Task Title Overflow")
@Composable
private fun PreviewTaskTitleOverFlow() {
    CheckoffTheme {
        TaskCard(
            task = TaskItem(
                title = "Go fishing asdhsajdhajksdjsahdaskjdasjhdjkashdkjashdkjashjkdasjkdhasjkhdjkashdkjashd",
                desc = "I love fishing.",
            ),
            onCheckBoxPressed = {},
        )
    }
}