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
import com.oscar.posadas.basetest.ui.PingViewEvent.ApprovePairingDevice
import com.oscar.posadas.basetest.ui.PingViewEvent.DismissAlert
import com.oscar.posadas.basetest.ui.PingViewEvent.GenerateMobilPayload
import com.oscar.posadas.basetest.ui.PingViewEvent.ProcessIdToken
import com.oscar.posadas.basetest.ui.PingViewEvent.StartPassCodeSequence
import com.oscar.posadas.basetest.ui.PingViewEvent.UpdateIdToken
import com.oscar.posadas.basetest.ui.common.ConfirmationDialog
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PingView(
    vmState: StateFlow<PingViewModelState>,
    onPingEvent: (PingViewEvent) -> Unit
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
            getMobilePayload = { onPingEvent(GenerateMobilPayload) }
        )
        ProcessIdTokenView(
            processIdToken = { onPingEvent(ProcessIdToken) },
            onIdTokenChange = { onPingEvent(UpdateIdToken(it)) },
            idToken = state.value.idToken
        )
        PasscodeSequenceView(
            startPasscodeSequence = { onPingEvent(StartPassCodeSequence) },
            passcode = state.value.passcode,
            passcodeTimer = state.value.passcodeTimer
        )
        if (state.value.showAllowPairingDialog) {
            val successMsg = stringResource(id = R.string.app_device_paired_successfully)
            ConfirmationDialog(
                title = stringResource(id = R.string.app_allow_pairing_question),
                description = stringResource(id = R.string.app_allow_pairing_description),
                onDismissDialog = { onPingEvent(DismissAlert) },
                onConfirm = { onPingEvent(ApprovePairingDevice(successMsg)) }
            )
        }
        if (state.value.alertMsg != null) {
            ConfirmationDialog(
                title = "",
                description = state.value.alertMsg.orEmpty(),
                onDismissDialog = { onPingEvent(DismissAlert) },
                onConfirm = { onPingEvent(DismissAlert) },
                showCancelButton = false
            )
        }
    }
}