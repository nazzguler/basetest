package com.oscar.posadas.basetest.model

import com.pingidentity.pingidsdkv2.types.OneTimePasscodeInfo

data class GetOneTimePasscodeResponse(
    val oneTimePasscodeInfo: OneTimePasscodeInfo? = null,
    val error: String? = null
)
