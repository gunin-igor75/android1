package ru.it_cron.intern1.data.network.dto.company_dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("Id") val id: String,
    @SerializedName("CustomerName") val customerName: String,
    @SerializedName("Company") val company: String,
    @SerializedName("Text") val text: String,
    @SerializedName("Icon") val urlIcon: String,
)
