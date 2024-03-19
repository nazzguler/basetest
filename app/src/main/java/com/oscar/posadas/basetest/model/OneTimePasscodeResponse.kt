package com.oscar.posadas.basetest.model

import com.pingidentity.pingidsdkv2.PingOneSDKError
import com.pingidentity.pingidsdkv2.types.OneTimePasscodeInfo

data class OneTimePasscodeResponse(
    val oneTimePasscodeInfo: OneTimePasscodeInfo? = null,
    val error: PingOneSDKError? = null
)
