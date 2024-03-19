package com.oscar.posadas.basetest.model

import com.pingidentity.pingidsdkv2.PairingObject
import com.pingidentity.pingidsdkv2.PingOneSDKError

data class ProcessIdTokenResponse(
    val error: PingOneSDKError? = null,
    val pairingObject: PairingObject? = null
)
