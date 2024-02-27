package com.oscar.posadas.basetest.di

import com.oscar.posadas.basetest.network.RetrofitManager
import com.oscar.posadas.basetest.network.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesRandomUsersApi(): UsersApi {
        return RetrofitManager
            .getRetrofit()
            .create(UsersApi::class.java)
    }
}