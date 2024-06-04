package ru.it_cron.intern1.data.network.dto.case_dto

import com.google.gson.annotations.SerializedName

data class PlatformsDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
)
