package ru.it_cron.android1.data.network.dto.filter_dto

import com.google.gson.annotations.SerializedName
import ru.it_cron.android1.data.network.dto.cases_dto.FilterDto

data class DataFiltersDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Filters") val filters: List<FilterDto>
)
