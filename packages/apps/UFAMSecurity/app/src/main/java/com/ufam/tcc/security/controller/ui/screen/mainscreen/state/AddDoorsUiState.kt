package com.ufam.tcc.security.controller.ui.screen.mainscreen.state

data class AddDoorsUiState(
    val isValid: Boolean = false,
    val textValue: String = "",
    val onTextChange: (String) -> Unit = {},
    val onClear: () -> Unit = {},
    val onApply: (() -> Unit) -> Unit = {},
)