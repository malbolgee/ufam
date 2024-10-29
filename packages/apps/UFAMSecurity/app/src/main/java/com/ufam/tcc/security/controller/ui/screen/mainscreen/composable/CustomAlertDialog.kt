package com.ufam.tcc.security.controller.ui.screen.mainscreen.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.ufam.tcc.security.controller.R
import com.ufam.tcc.security.controller.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    show: Boolean = true,
    title: String = stringResource(R.string.lorem_ipsum_title),
    body: String = stringResource(R.string.lorem_ipsum_description),
    buttonText: String = stringResource(R.string.lorem_ipsum_title),
    secondText: String = "",
    icon: Painter? = null,
    cancelButton: String = stringResource(R.string.lorem_ipsum_title),
    onDismissRequest: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    dismissOnClickOutside: Boolean = false,
) {

    if (show) {
        BasicAlertDialog(
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
            properties = DialogProperties(
                dismissOnClickOutside = dismissOnClickOutside,
                dismissOnBackPress = false,
            )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                if (icon != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { onDismissRequest() },
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            painter = icon,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = body,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
                if (secondText.isNotBlank()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = secondText,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                    onClick = { onButtonClick() },
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.inversePrimary,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        text = buttonText,
                    )
                }
                if (cancelButton.isNotBlank()) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                        onClick = { onDismissRequest() },
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            text = cancelButton,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewNight() {
    AppTheme {
        CustomAlertDialog()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        CustomAlertDialog()
    }
}