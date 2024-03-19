package com.oscar.posadas.basetest.repository

import android.content.Context
import com.oscar.posadas.basetest.model.ApprovePairingDeviceResponse
import com.oscar.posadas.basetest.model.OneTimePasscodeResponse
import com.oscar.posadas.basetest.model.ProcessIdTokenResponse
import com.pingidentity.pingidsdkv2.PairingObject

interface PingOneRepository {

    fun getMobilePayload(): String

    suspend fun processIdToken(token: String): ProcessIdTokenResponse

    suspend fun approvePairingDevice(pairingObject: PairingObject): ApprovePairingDeviceResponse

    suspend fun getOneTimePasscode(): OneTimePasscodeResponse

    companion object {
        fun getPingOneRepositoryImpl(context: Context): PingOneRepository {
            return PingOneRepositoryImpl(context)
        }
    }
}