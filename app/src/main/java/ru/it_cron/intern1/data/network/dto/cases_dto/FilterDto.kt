package ru.it_cron.intern1.data.network.dto.cases_dto

import com.google.gson.annotations.SerializedName

data class FilterDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name :String
)