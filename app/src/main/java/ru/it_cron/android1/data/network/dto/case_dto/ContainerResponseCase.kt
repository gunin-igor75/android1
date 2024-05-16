package ru.it_cron.android1.data.network.dto.case_dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseCase(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataDetailsCaseDto: DataDetailsCaseDto,
)
