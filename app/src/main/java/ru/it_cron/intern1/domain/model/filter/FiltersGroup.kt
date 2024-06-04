package ru.it_cron.intern1.domain.model.filter

data class FiltersGroup(
    val id: String,
    val name: String,
    val filters: List<Filter>,
)
