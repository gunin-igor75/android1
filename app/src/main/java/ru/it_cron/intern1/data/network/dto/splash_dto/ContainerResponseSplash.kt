package ru.it_cron.intern1.data.network.dto.splash_dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseSplash(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataAvailableDto: DataAvailableDto
)
