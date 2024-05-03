package ru.it_cron.android1.data.network.dto

import com.google.gson.annotations.SerializedName

data class DataCasesDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Image") val image: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Filters") val filters: List<FilterDto>
)
