package ru.it_cron.android1.domain.model

data class CaseDetails(
    val id: String,
    val title: String,
    val images: List<String>,
    val task: String,
    val technologies: List<String>,
    val platforms: List<String>,
    val featureTitle: String,
    val featuresDone: List<String>,
    val caseColorId: Int,
    val iOSUrl: String,
    val androidUrl: String,
    val webUrl: String
)
