package ru.it_cron.android1.data.network.dto.case_dto

import com.google.gson.annotations.SerializedName

data class DataDetailsCaseDto(
    @SerializedName("Id") val id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Images") val images: List<String>?,
    @SerializedName("Task") val task: String?,
    @SerializedName("Technologies") val technologies: List<TechnologiesDto>?,
    @SerializedName("Platforms") val platforms: List<PlatformsDto>?,
    @SerializedName("FeaturesTitle") val featureTitle: String?,
    @SerializedName("FeaturesDone") val featuresDone: List<String>?,
    @SerializedName("CaseColor") val caseColor: String,
    @SerializedName("iOSUrl") val iOSUrl: String?,
    @SerializedName("AndroidUrl") val androidUrl: String?,
    @SerializedName("WebUrl") val webUrl: String?
)
