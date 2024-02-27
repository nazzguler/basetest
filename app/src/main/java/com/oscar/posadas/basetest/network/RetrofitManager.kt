package com.oscar.posadas.basetest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    companion object {
        private const val usersBaseUrl = "https://randomuser.me"
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(usersBaseUrl)
                .build()
        }
    }
}