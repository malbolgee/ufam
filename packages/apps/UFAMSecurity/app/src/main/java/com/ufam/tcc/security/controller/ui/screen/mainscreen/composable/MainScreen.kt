package com.ufam.tcc.security.controller.ui.screen.mainscreen.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ufam.tcc.security.controller.R
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.ClearDialogUiState
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.MainScreenUiState
import com.ufam.tcc.security.controller.ui.theme.AppTheme

@Composable
fun MainScreen(
    state: MainScreenUiState = MainScreenUiState(),
    dialogState: ClearDialogUiState = ClearDialogUiState(),
    onNavigateToAddScreen: () -> Unit = {},
) {

    val listState = rememberLazyListState()

    CustomAlertDialog(
        show = dialogState.isShown,
        title = stringResource(R.string.clear_dialog_title),
        body = stringResource(R.string.clear_dialog_description),
        buttonText = stringResource(R.string.clear_dialog_ok_button),
        cancelButton = stringResource(R.string.clear_dialog_cancel_button),
        icon = rememberVectorPainter(Icons.Default.Warning),
        onDismissRequest = { dialogState.onCancel() },
        onButtonClick = { dialogState.onOk() }
    )

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(120.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (state.openDoors.isEmpty()) {
                Icon(
                    modifier = Modifier
                        .size(120.dp)
                        .alpha(.2f),
                    painter = painterResource(R.drawable.code_off_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    state = listState,
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {

                    items(state.openDoors, key = { it.number }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(60.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        ) {
                            Row(horizontalArrangement = Arrangement.Center) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                        .weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${it.number}"
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(end = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (it.isClosed) Icons.Filled.Lock else Icons.Filled.LockOpen,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        HorizontalDivider(thickness = Dp.Hairline)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                enabled = state.openDoors.isNotEmpty(),
                onClick = { state.onClear() }) {
                Icon(Icons.Default.Clear, contentDescription = null)
            }
            Button(onClick = onNavigateToAddScreen) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@Composable
private fun TopImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.marca_ufam_vetor_positivo_ai__1_),
        contentDescription = null,
    )
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        MainScreen()
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun PreviewNight() {
    AppTheme {
        MainScreen()
    }
}
