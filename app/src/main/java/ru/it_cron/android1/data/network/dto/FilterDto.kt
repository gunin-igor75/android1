package ru.it_cron.android1.data.network.dto

import com.google.gson.annotations.SerializedName

data class FilterDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name :String
)
