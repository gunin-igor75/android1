package ru.it_cron.android1.data.network.dto.app_dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseApp(
    @SerializedName("Error") val errorDto: ErrorDto?,
    @SerializedName("Data") val data: String,
)