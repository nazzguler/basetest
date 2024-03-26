package com.oscar.posadas.basetest.ui

import com.oscar.posadas.basetest.R

sealed class PassCodeState(val resId: Int? = null) {
    data object PasscodeError : PassCodeState(R.string.app_passcode_error)

    data object NotInitialized : PassCodeState(R.string.app_passcode_not_initialized)

    class PassCodeReceived(val passcode: String) : PassCodeState()
}