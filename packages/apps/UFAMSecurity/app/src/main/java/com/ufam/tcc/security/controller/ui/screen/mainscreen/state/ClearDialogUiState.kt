package com.ufam.tcc.security.controller.ui.screen.mainscreen.state

data class ClearDialogUiState(
    val isShown: Boolean = false,
    val onOk: () -> Unit = {},
    val onCancel: () -> Unit = {},
)
