package com.oscar.posadas.basetest.ui

import com.pingidentity.pingidsdkv2.PairingObject

data class PingViewModelState(
    val alertMsg: String? = null,
    val mobilePayload: String? = null,
    val idToken: String? = null,
    val showAllowPairingDialog: Boolean = false,
    val pairingObject: PairingObject? = null
)