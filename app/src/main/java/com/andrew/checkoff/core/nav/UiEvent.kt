package com.andrew.checkoff.core.nav

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(
        val message: String,
        val actionMsg: String? = null
    ) : UiEvent()
}