package com.ufam.tcc.security.controller.ui.screen.mainscreen.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ufam.tcc.security.controller.api.IAppDataRepository
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.AddDoorsUiState
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.ClearDialogUiState
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.DoorItem
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.MainScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val context: Application,
    private val appDataRepository: IAppDataRepository,
) : ViewModel() {

    companion object {
        private val LOG_TAG = MainScreenViewModel::class.simpleName
    }

    private var internalDoorList: List<DoorItem> = listOf()

    private val _mainScreenUiState = MutableStateFlow(
        MainScreenUiState(
            openDoors = internalDoorList,
            onAdd = ::onAdd,
            onClear = ::onClear,
            onApply = ::onApply
        )
    )

    private val _addDoorsUiState = MutableStateFlow(
        AddDoorsUiState(
            onTextChange = ::onTextChange,
            onClear = ::onAddDoorsClear,
            onApply = ::onAddDoorsApply,
        )
    )

    private val _clearDialogUiState = MutableStateFlow(
        ClearDialogUiState(
            onCancel = ::onCancelClickOnDialog,
            onOk = ::onOkClickOnDialog,
        )
    )

    init {
        updateOpenDoors()
    }

    val mainScreenUiState get() = _mainScreenUiState.asStateFlow()
    val addDoorsUiState get() = _addDoorsUiState.asStateFlow()
    val clearDialogUiState get() = _clearDialogUiState.asStateFlow()

    private fun onAdd() {
    }

    private fun onClear() {
        _clearDialogUiState.update { state ->
            state.copy(isShown = true)
        }
    }

    private fun onApply() {
        viewModelScope.launch {
            appDataRepository.closeAllBut(internalDoorList.map { it.number }.toIntArray())
            internalDoorList = internalDoorList.map { DoorItem(it.number, false) }
            _mainScreenUiState.update { state ->
                state.copy(openDoors = internalDoorList)
            }
        }
    }

    private fun updateOpenDoors() {
        viewModelScope.launch {
            internalDoorList = appDataRepository.getAllOpenDoors().map { DoorItem(number = it) }
                .sortedBy { it.number }
            _mainScreenUiState.update { state ->
                state.copy(openDoors = internalDoorList)
            }
        }
    }

    private fun onTextChange(value: String) {
        Log.d(LOG_TAG, "Value received: $value")
        _addDoorsUiState.update { state ->
            state.copy(
                textValue = value,
                isValid = checkIfValid(value)
            )
        }
    }

    private fun onAddDoorsClear() {
        _addDoorsUiState.update { state ->
            state.copy(textValue = "")
        }
    }

    private fun onAddDoorsApply(onBackPage: () -> Unit = {}) {
        viewModelScope.launch {
            val hasOpenDoors = appDataRepository.getAllOpenDoors().isNotEmpty()

            internalDoorList += fromStringToList(_addDoorsUiState.value.textValue).map {
                DoorItem(
                    it,
                    isClosed = hasOpenDoors
                )
            }

            internalDoorList = removeDuplicates(internalDoorList)

            _mainScreenUiState.update { state ->
                state.copy(openDoors = internalDoorList)
            }
            _addDoorsUiState.update { state ->
                state.copy(textValue = "")
            }

            onApply()
            Toast.makeText(context, "Ports added", Toast.LENGTH_LONG).show()
            onBackPage()
        }
    }

    private fun onOkClickOnDialog() {
        _mainScreenUiState.update { state ->
            internalDoorList = listOf()
            state.copy(
                openDoors = internalDoorList,
                canClear = false
            )
        }
        _clearDialogUiState.update { state ->
            state.copy(
                isShown = false,
            )
        }
        onApply()
    }

    private fun onCancelClickOnDialog() {
        _clearDialogUiState.update { state ->
            state.copy(isShown = false)
        }
    }

    private fun removeDuplicates(values: List<DoorItem>): List<DoorItem> =
        values.groupBy { it.number }
            .map { (_, group) ->
                group.find { it.isClosed } ?: group.first()
            }

    private fun checkIfValid(values: String): Boolean =
        values.isNotEmpty() && values.split(Regex("[, ]")).map { it.trim() }
            .filter { it.isNotEmpty() }
            .all { it.isDigitsOnly() && (it.toInt() in 1025..65534) }

    private fun fromStringToList(value: String): List<Int> =
        value.split(Regex("[, ]")).map { it.trim() }.filter { it.isNotEmpty() }.map { it.toInt() }

}