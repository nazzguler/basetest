package com.oscar.posadas.basetest.model

import com.pingidentity.pingidsdkv2.PingOneSDKError
import com.pingidentity.pingidsdkv2.types.PairingInfo

data class ApprovePairingDeviceResponse(
    val pairingInfo: PairingInfo? = null,
    val error: PingOneSDKError? = null
)
