package com.oscar.posadas.basetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.oscar.posadas.basetest.presentation.UsersScreen
import com.oscar.posadas.basetest.presentation.UsersViewModel
import com.oscar.posadas.basetest.ui.theme.BaseTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTestTheme {
                val state = vm.state
                state.value.users?.let { list ->
                    UsersScreen(users = list)
                }
            }
        }
    }
}