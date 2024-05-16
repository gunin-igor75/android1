package ru.it_cron.android1.data.network.dto

import com.google.gson.annotations.SerializedName

data class DataAvailableDto(
    @SerializedName("IsCabinetAvailable") val isCabinetAvailable: Boolean
)
