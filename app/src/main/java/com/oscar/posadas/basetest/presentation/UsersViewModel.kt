package com.oscar.posadas.basetest.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscar.posadas.basetest.network.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(remote: UsersRepository) : ViewModel() {
    val state = mutableStateOf(UsersState())

    init {
        state.value = state.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = remote.getUsers(20) // hardcoded for now
            state.value = state.value.copy(users = response.results, isLoading = false)
        }
        // coroutine tests
        val flow1 = (1..10).asFlow().onEach{delay(1000L)}
        val flow2 = (1..10).asFlow().onEach{delay(2000L)}
        val scope = CoroutineScope(Dispatchers.IO)
        flow1.zip(flow2) { n1,n2 ->
            println("$n1, $n2")
        }.launchIn(scope)
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            coroutineScope {
                var count = 1
                while(count<=10) {
                    println(count)
                    delay(1000L)
                    count++
                }
            }
        }
    }

}