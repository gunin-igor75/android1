package ru.it_cron.android1.data.network.dto

import com.google.gson.annotations.SerializedName

data class ContainerResponse(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataDto: DataDto
)
