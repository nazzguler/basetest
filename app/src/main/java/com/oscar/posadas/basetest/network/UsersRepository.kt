package com.oscar.posadas.basetest.network

import javax.inject.Inject

class UsersRepository @Inject constructor(private val api: UsersApi) {
    suspend fun getUsers(results: Int): Response<List<UserDto>> = api.getUsers(results)
}