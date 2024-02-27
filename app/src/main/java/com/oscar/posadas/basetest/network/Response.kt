package com.oscar.posadas.basetest.network

import com.google.gson.annotations.Expose

data class Response<T>(
    @Expose
    val results: T
)
