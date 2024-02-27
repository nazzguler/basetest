package com.oscar.posadas.basetest.presentation

import com.oscar.posadas.basetest.network.UserDto

data class UsersState(
    val isLoading: Boolean = false,
    val users: List<UserDto>? = null
)
