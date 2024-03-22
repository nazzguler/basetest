package com.oscar.posadas.basetest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.oscar.posadas.basetest.repository.PingOneRepository
import com.oscar.posadas.basetest.ui.theme.BaseTestTheme

internal class PingActivity : ComponentActivity() {

    private val vm by lazy {
        PingViewModel(
            pingOneRepository = PingOneRepository.getPingOneRepositoryImpl(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    vm.run {
                        PingView(
                            vmState = state,
                            onPingEvent = this::processEvent
                        )
                    }
                }
            }
        }
    }
}