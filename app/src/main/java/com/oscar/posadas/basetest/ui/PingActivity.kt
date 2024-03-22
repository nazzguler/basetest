package com.oscar.posadas.basetest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.oscar.posadas.basetest.R
import com.oscar.posadas.basetest.repository.PingOneRepository
import com.oscar.posadas.basetest.ui.theme.BaseTestTheme

internal class PingActivity : ComponentActivity() {

    private val vm by lazy {
        PingViewModel(
            pingOneRepository = PingOneRepository.getPingOneRepositoryImpl(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    vm.run {
                        PingView(
                            vmState = state,
                            getMobilePayload = this::generateMobilePayload,
                            onIdTokenChange = this::updateIdToken,
                            processIdToken = this::processIdToken,
                            dismissAllowPairingDialog = { allowPairingDialogVisibility(isVisible = false) },
                            approvePairingDevice = { approvePairingDevice(getString(R.string.app_device_paired_successfully)) },
                            dismissAlert = this::dismissAlert,
                            startPasscodeSequence = this::startPassCodeSequence
                        )
                    }
                }
            }
        }
    }
}