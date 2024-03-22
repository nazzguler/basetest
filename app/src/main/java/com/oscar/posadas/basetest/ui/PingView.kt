package com.oscar.posadas.basetest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oscar.posadas.basetest.R
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
    dismissAlert: () -> Unit,
    startPasscodeSequence: () -> Unit
) {
    val state = vmState.collectAsState()

    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )

    ) {
        GenerateMobilePayloadView(
            mobilePayload = state.value.mobilePayload,
            getMobilePayload = getMobilePayload
        )
        ProcessIdTokenView(
            processIdToken = processIdToken,
            onIdTokenChange = onIdTokenChange,
            idToken = state.value.idToken
        )
        PasscodeSequenceView(
            startPasscodeSequence = startPasscodeSequence,
            passcode = state.value.passcode,
            passcodeTimer = state.value.passcodeTimer
        )
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