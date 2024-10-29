package com.ufam.tcc.security.controller.ui.screen.mainscreen.state

import kotlin.random.Random

data class MainScreenUiState(
    val openDoors: List<DoorItem> = List(10) { _ -> DoorItem() },
    val canClear: Boolean = true,
    val onAdd: () -> Unit = {},
    val onClear: () -> Unit = {},
    val onApply: () -> Unit = {},
)

data class DoorItem(
    val number: Int = Random.nextInt(1024, 65535),
    val isClosed: Boolean = false,
)
