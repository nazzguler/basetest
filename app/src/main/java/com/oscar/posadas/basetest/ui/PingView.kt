package com.oscar.posadas.basetest.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oscar.posadas.basetest.R
import com.oscar.posadas.basetest.ui.common.Button
import com.oscar.posadas.basetest.ui.common.ConfirmationDialog
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PingView(
    vmState: StateFlow<PingViewModelState>,
    getMobilePayload: () -> Unit,
    processIdToken: () -> Unit,
    onIdTokenChange: (String) -> Unit,
    dismissAllowPairingDialog: () -> Unit,
    approvePairingDevice: () -> Unit,
    dismissAlert: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val state = vmState.collectAsState()

    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )

    ) {
        Button(
            onClick = getMobilePayload,
            text = stringResource(id = R.string.app_generate_mobile_payload)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            backgroundColor = Color(0xFFA8DBE5)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        clipboardManager.setText(AnnotatedString(state.value.mobilePayload.orEmpty()))
                    }, textAlign = TextAlign.Center, text = state.value.mobilePayload.orEmpty()
            )
        }
        Button(
            onClick = processIdToken, text = stringResource(id = R.string.app_process_id_token)
        )
        Card(
            modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xFFA8DBE5)
        ) {
            TextField(
                value = state.value.idToken.orEmpty(),
                placeholder = { Text(stringResource(R.string.app_paste_id_token)) },
                onValueChange = { onIdTokenChange(it) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
        }
        if (state.value.showAllowPairingDialog) {
            ConfirmationDialog(
                title = stringResource(id = R.string.app_allow_pairing_question),
                description = stringResource(id = R.string.app_allow_pairing_description),
                onDismissDialog = { dismissAllowPairingDialog() },
                onConfirm = { approvePairingDevice() }
            )
        }
        if (state.value.alertMsg != null) {
            ConfirmationDialog(
                title = "",
                description = state.value.alertMsg.orEmpty(),
                onDismissDialog = { dismissAlert() },
                onConfirm = { dismissAlert() },
                showCancelButton = false
            )
        }
    }
}