package com.oscar.posadas.basetest.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.oscar.posadas.basetest.R

@Composable
fun ConfirmationDialog(
    title: String,
    description: String,
    onDismissDialog: () -> Unit,
    onConfirm: () -> Unit,
    showCancelButton: Boolean = true
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = Bold
                ),
                modifier = Modifier.padding(
                    top = 8.dp
                )
            )
        },
        text = {
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = Bold
                ),
                modifier = Modifier.padding(
                    top = 8.dp
                )
            )
        },
        onDismissRequest = { onDismissDialog() },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.app_confirmation_dialog_ok),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = Bold,
                    color = Color(0xFF00577D),
                ),
                modifier = Modifier
                    .padding(
                        bottom = 16.dp,
                        end = 16.dp
                    )
                    .clickable {
                        onConfirm()
                    }
            )
        },
        dismissButton = {
            if (showCancelButton) {
                Text(
                    text = stringResource(id = R.string.app_confirmation_dialog_cancel),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = Bold,
                        color = Color(0xFF00577D),
                    ),
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                            end = 16.dp
                        )
                        .clickable {
                            onDismissDialog()
                        }
                )
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}