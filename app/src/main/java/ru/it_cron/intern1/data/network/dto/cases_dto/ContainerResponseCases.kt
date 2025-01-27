package ru.it_cron.intern1.data.network.dto.cases_dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseCases(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataCasesDto: List<DataCasesDto>
)
