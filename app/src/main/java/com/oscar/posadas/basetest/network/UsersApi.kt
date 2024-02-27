package com.oscar.posadas.basetest.network

import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("/api")
    suspend fun getUsers(
        @Query("results") results: Int
    ): Response<List<UserDto>>
}