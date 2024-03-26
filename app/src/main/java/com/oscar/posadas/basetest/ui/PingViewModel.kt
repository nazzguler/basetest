package com.oscar.posadas.basetest.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.oscar.posadas.basetest.mvi.ViewModel
import com.oscar.posadas.basetest.repository.PingOneRepository
import com.oscar.posadas.basetest.ui.PassCodeState.NotInitialized
import com.oscar.posadas.basetest.ui.PassCodeState.PassCodeReceived
import com.oscar.posadas.basetest.ui.PassCodeState.PasscodeError
import com.oscar.posadas.basetest.ui.PingViewEvent.AllowPairingDialogVisibility
import com.oscar.posadas.basetest.ui.PingViewEvent.ApprovePairingDevice
import com.oscar.posadas.basetest.ui.PingViewEvent.DismissAlert
import com.oscar.posadas.basetest.ui.PingViewEvent.GenerateMobilPayload
import com.oscar.posadas.basetest.ui.PingViewEvent.ProcessIdToken
import com.oscar.posadas.basetest.ui.PingViewEvent.StartPassCodeSequence
import com.oscar.posadas.basetest.ui.PingViewEvent.UpdateIdToken
import com.oscar.posadas.basetest.utils.MILLIS
import com.pingidentity.pingidsdkv2.types.OneTimePasscodeInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class PingViewModel(
    private val pingOneRepository: PingOneRepository
) : ViewModel<PingViewModelState, PingViewEvent>(PingViewModelState()) {

    private val tag = PingViewModel::class.java.name
    override fun processEvent(event: PingViewEvent) {
        when (event) {
            is AllowPairingDialogVisibility -> allowPairingDialogVisibility(event.isVisible)
            is ApprovePairingDevice -> approvePairingDevice(event.successMsg)
            DismissAlert -> dismissAlert()
            GenerateMobilPayload -> generateMobilePayload()
            ProcessIdToken -> processIdToken()
            StartPassCodeSequence -> startPassCodeSequence()
            is UpdateIdToken -> updateIdToken(event.token)
        }
    }

    private fun generateMobilePayload() {
        _state.value = _state.value.copy(mobilePayload = pingOneRepository.getMobilePayload())
    }

    private fun updateIdToken(token: String?) {
        _state.value = _state.value.copy(idToken = token)
    }

    private fun allowPairingDialogVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(showAllowPairingDialog = isVisible)
    }

    private fun dismissAlert() {
        _state.value = _state.value.copy(alertMsg = null)
    }

    private fun processIdToken() {
        viewModelScope.launch {
            var log = "processIdToken() -> "
            try {
                val response = pingOneRepository.processIdToken(state.value.idToken.orEmpty())
                if (response.error != null) {
                    log += "error: ${response.error}"
                    Log.e(tag, log)
                    _state.value = _state.value.copy(alertMsg = response.error.message)
                }
                if (response.pairingObject != null) {
                    log += "success: ${response.pairingObject}"
                    Log.i(tag, log)
                    _state.value = _state.value.copy(
                        pairingObject = response.pairingObject,
                        showAllowPairingDialog = true
                    )
                }
            } catch (e: Exception) {
                log += "error: $e"
                Log.e(tag, log)
                _state.value = _state.value.copy(alertMsg = e.message)
            }
        }
    }

    private fun approvePairingDevice(successMsg: String) {
        viewModelScope.launch {
            state.value.pairingObject?.let {
                var log = "approvePairingInfo() -> "
                val pairingResponse = pingOneRepository.approvePairingDevice(it)
                _state.value = if (pairingResponse.error != null) {
                    log += "error: ${pairingResponse.error}"
                    Log.e(tag, log)
                    _state.value.copy(alertMsg = pairingResponse.error.message)
                } else {
                    log += "success: ${pairingResponse.pairingInfo}"
                    Log.i(tag, log)
                    _state.value.copy(alertMsg = successMsg, isDevicePaired = true)
                }
                _state.value = _state.value.copy(showAllowPairingDialog = false)
            }
        }
    }

    private fun startPassCodeSequence() {
        if (state.value.isDevicePaired) {
            viewModelScope.launch {
                val r = pingOneRepository.getOneTimePasscode()
                r.oneTimePasscodeInfo?.let {
                    startAnimation(it)
                } ?: run {
                    r.error?.let {
                        _state.value.copy(alertMsg = it.message)
                    }
                    _state.value.copy(passcode = PasscodeError, passcodeTimer = "")
                }
            }
        } else {
            _state.value = _state.value.copy(passcode = NotInitialized, passcodeTimer = "")
        }
    }

    private suspend fun startAnimation(otpData: OneTimePasscodeInfo) {
        val runTime = (otpData.validUntil * MILLIS - System.currentTimeMillis()).toLong()
        coroutineScope {
            _state.value = _state.value.copy(passcode = PassCodeReceived(otpData.passcode))
            object : CountDownTimer(runTime, MILLIS) {
                override fun onTick(millisUntilFinished: Long) {
                    _state.value =
                        _state.value.copy(passcodeTimer = ((millisUntilFinished / MILLIS)).toString() + "s")
                }

                override fun onFinish() {
                    if (!coroutineContext.isActive) {
                        startPassCodeSequence()
                    }
                }
            }
        }
    }

}