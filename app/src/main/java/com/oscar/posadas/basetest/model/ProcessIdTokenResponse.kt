package com.oscar.posadas.basetest.model

import com.pingidentity.pingidsdkv2.PairingObject

data class ProcessIdTokenResponse(
    val error: String? = null,
    val pairingObject: PairingObject? = null
)
