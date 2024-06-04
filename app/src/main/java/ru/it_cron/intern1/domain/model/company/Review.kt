package ru.it_cron.intern1.domain.model.company

data class Review(
    val id: String,
    val customerName: String,
    val company: String,
    val text: String,
    val urlIcon: String,
)
