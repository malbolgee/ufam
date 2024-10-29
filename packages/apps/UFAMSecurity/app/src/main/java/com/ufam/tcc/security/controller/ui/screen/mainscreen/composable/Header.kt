package com.ufam.tcc.security.controller.ui.screen.mainscreen.composable

import android.util.LayoutDirection
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.core.text.layoutDirection
import java.util.Locale

@Composable
fun Header(
    title: String = "",
    onBackClick: () -> Unit = {},
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBar(onBackClick)
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 5.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
fun TopBar(onBackNavigation: () -> Unit = {}) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        IconButton(onClick = { onBackNavigation() }) {
            Icon(
                modifier =
                Modifier.scale(
                    if (Locale.getDefault().layoutDirection == LayoutDirection.RTL) -1f else 1f,
                    1f,
                ),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}