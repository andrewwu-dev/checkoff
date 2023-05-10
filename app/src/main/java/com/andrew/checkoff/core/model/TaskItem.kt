package com.andrew.checkoff.core.model

data class TaskItem(
    val title: String,
    val desc: String,
    val completed: Boolean = false,
)