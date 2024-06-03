package ru.it_cron.intern1.data.network.dto.cases_dto

import com.google.gson.annotations.SerializedName

data class DataCasesDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Image") val image: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Filters") val filters: List<FilterDto>
)
