package com.andrew.checkoff.feature.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrew.checkoff.R
import com.andrew.checkoff.core.theme.CheckoffTheme

@Composable
internal fun TaskCard(
    title: String,
    desc: String,
) {
    val checkedState = remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(bottom = 10.dp, end = 10.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
        ) {
            CircleCheckbox(
                selected = checkedState.value,
                onChecked = { checkedState.value = !checkedState.value },
            )
            Column(
                modifier = Modifier.padding(start = 0.dp, top = 13.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, maxLines = 2)
                Text(text = desc, style = MaterialTheme.typography.bodySmall, maxLines = 1)
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(Color.White, shape = CircleShape)
                    .fillMaxSize(0.6f),
                contentDescription = "Checkmark",
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.baseline_radio_button_unchecked_24),
                tint = Color.Gray,
                modifier = Modifier
                    .background(Color.Transparent, shape = CircleShape)
                    .fillMaxSize(0.6f),
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
            "Go fishing",
            "I love fishing."
        )
    }
}

@Preview("Task Title Overflow")
@Composable
private fun PreviewTaskTitleOverFlow() {
    CheckoffTheme {
        TaskCard(
            "Go fishing asdhsajdhajksdjsahdaskjdasjhdjkashdkjashdkjashjkdasjkdhasjkhdjkashdkjashd",
            "I love fishing.",
        )
    }
}