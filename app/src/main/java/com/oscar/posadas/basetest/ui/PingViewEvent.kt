package com.oscar.posadas.basetest.ui

import com.oscar.posadas.basetest.mvi.ViewEvent

sealed class PingViewEvent : ViewEvent {
    data object GenerateMobilPayload : PingViewEvent()
    class UpdateIdToken(val token: String?) : PingViewEvent()
    class AllowPairingDialogVisibility(val isVisible: Boolean) : PingViewEvent()
    data object DismissAlert : PingViewEvent()
    data object ProcessIdToken : PingViewEvent()
    class ApprovePairingDevice(val successMsg: String) : PingViewEvent()
    data object StartPassCodeSequence : PingViewEvent()
}