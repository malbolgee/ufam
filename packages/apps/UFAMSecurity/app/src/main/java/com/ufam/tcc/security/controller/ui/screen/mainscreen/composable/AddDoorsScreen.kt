package com.ufam.tcc.security.controller.ui.screen.mainscreen.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ufam.tcc.security.controller.R
import com.ufam.tcc.security.controller.ui.screen.mainscreen.state.AddDoorsUiState
import com.ufam.tcc.security.controller.ui.theme.AppTheme

@Composable
fun AddDoorsScreen(
    state: AddDoorsUiState = AddDoorsUiState(),
    onBackClick: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Header(
            title = stringResource(R.string.add_doors_screen_title),
            onBackClick = onBackClick
        )
        Textarea(state)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { state.onApply(onBackClick) },
                enabled = state.isValid
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
            }
            Button(
                onClick = { state.onClear() },
            ) {
                Icon(Icons.Default.Clear, contentDescription = null)
            }
        }
    }
}

@Composable
fun Textarea(state: AddDoorsUiState = AddDoorsUiState()) {
    Surface(
        modifier = Modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(20.dp),
    ) {
        TextField(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(80.dp)
                .border(
                    width = .8.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(percent = 30),
                ),
            textStyle = MaterialTheme.typography.bodyMedium,
            value = state.textValue,
            onValueChange = { state.onTextChange(it) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onBackground,
            ),
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun Preview() {
    AppTheme {
        AddDoorsScreen()
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun PreviewNight() {
    AppTheme {
        AddDoorsScreen()
    }
}
