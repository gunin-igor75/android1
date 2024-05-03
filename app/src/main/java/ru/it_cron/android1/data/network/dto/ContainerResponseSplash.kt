package ru.it_cron.android1.data.network.dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseSplash(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataAvailableDto: DataAvailableDto
)
