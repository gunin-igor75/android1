package ru.it_cron.android1.data.network.dto.company_dto

import com.google.gson.annotations.SerializedName

data class ContainerResponseReviews(
    @SerializedName("Data") val reviews: List<ReviewDto>?,
)
