package ru.it_cron.android1.data.network.dto.filter_dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseFilters(
    @SerializedName("Error") val error: String?,
    @SerializedName("Data") val dataFiltersDto: List<DataFiltersDto>,
)
