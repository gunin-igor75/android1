package ru.it_cron.intern1.data.network.dto.app_dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("Message") val message: String,
    @SerializedName("Code") val code: Int,
)
