package com.oscar.posadas.basetest.repository

import android.content.Context
import com.oscar.posadas.basetest.model.ApprovePairingDeviceResponse
import com.oscar.posadas.basetest.model.OneTimePasscodeResponse
import com.oscar.posadas.basetest.model.ProcessIdTokenResponse
import com.pingidentity.pingidsdkv2.PairingObject
import com.pingidentity.pingidsdkv2.PingOne
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class PingOneRepositoryImpl(private val context: Context) : PingOneRepository {
    override fun getMobilePayload(): String = PingOne.generateMobilePayload(context)
    override suspend fun processIdToken(token: String): ProcessIdTokenResponse {
        return suspendCoroutine {
            var r = ProcessIdTokenResponse()
            PingOne.processIdToken(token) { pairingObject, error ->
                r = r.copy(error = error, pairingObject = pairingObject)
                it.resume(r)
            }
        }
    }

    override suspend fun approvePairingDevice(pairingObject: PairingObject): ApprovePairingDeviceResponse {
        return suspendCoroutine {
            var r = ApprovePairingDeviceResponse()
            pairingObject.approve(
                context
            ) { pairingInfo, error ->
                r = r.copy(error = error, pairingInfo = pairingInfo)
                it.resume(r)
            }
        }
    }

    override suspend fun getOneTimePasscode(): OneTimePasscodeResponse {
        return suspendCoroutine {
            var r = OneTimePasscodeResponse()
            PingOne.getOneTimePassCode(context) { oneTimePasswordInfo, error ->
                r = r.copy(error = error, oneTimePasscodeInfo = oneTimePasswordInfo)
                it.resume(r)
            }
        }
    }
}