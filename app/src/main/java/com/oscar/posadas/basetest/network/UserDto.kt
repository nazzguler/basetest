package com.oscar.posadas.basetest.network

import com.google.gson.annotations.Expose

data class UserDto(
    @Expose
    val gender: String?,
    @Expose
    val name: NameDto,
    @Expose
    val email: String?,
    @Expose
    val picture: PictureDto
)

data class NameDto(
    @Expose
    val title: String?,
    @Expose
    val first: String?,
    @Expose
    val last: String?
) {
    fun getDisplayedName() = """${title ?: ""} ${first ?: ""} ${last ?: ""}"""
}

data class PictureDto(
    @Expose
    val large: String?,
    @Expose
    val medium: String?,
    @Expose
    val thumbnail: String?
)
