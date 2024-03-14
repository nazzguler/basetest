package com.oscar.posadas.basetest.repository

import android.content.Context
import com.oscar.posadas.basetest.model.ApprovePairingDeviceResponse
import com.oscar.posadas.basetest.model.GetOneTimePasscodeResponse
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
                r = r.copy(error = error?.toString(), pairingObject = pairingObject)
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
                r = r.copy(error = error?.toString(), pairingInfo = pairingInfo)
                it.resume(r)
            }
        }
    }

    override suspend fun getOneTimePasscode(): GetOneTimePasscodeResponse {
        return suspendCoroutine {
            var r = GetOneTimePasscodeResponse()
            PingOne.getOneTimePassCode(context) { oneTimePasswordInfo, error ->
                r = r.copy(error = error?.toString(), oneTimePasscodeInfo = oneTimePasswordInfo)
                it.resume(r)
            }
        }
    }
}