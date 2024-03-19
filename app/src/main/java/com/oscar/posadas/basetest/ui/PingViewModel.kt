package com.oscar.posadas.basetest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar.posadas.basetest.repository.PingOneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class PingViewModel(
    private val pingOneRepository: PingOneRepository
) : ViewModel() {

    private val tag = PingViewModel::class.java.name
    private val _state = MutableStateFlow(PingViewModelState())
    val state: StateFlow<PingViewModelState>
        get() = _state.asStateFlow()

    fun generateMobilePayload() {
        _state.value = _state.value.copy(mobilePayload = pingOneRepository.getMobilePayload())
    }

    fun updateIdToken(token: String) {
        _state.value = _state.value.copy(idToken = token)
    }

    fun allowPairingDialogVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(showAllowPairingDialog = isVisible)
    }

    fun dismissAlert() {
        _state.value = _state.value.copy(alertMsg = null)
    }

    fun processIdToken() {
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

    fun approvePairingDevice(successMsg: String) {
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
                    _state.value.copy(alertMsg = successMsg)
                }
                _state.value = _state.value.copy(showAllowPairingDialog = false)
            }
        }
    }

}