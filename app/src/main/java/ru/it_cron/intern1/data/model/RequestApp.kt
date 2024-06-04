package ru.it_cron.intern1.data.model

import ru.it_cron.intern1.domain.model.app.FileItem

data class RequestApp(
    val task: String,
    val name: String,
    val company: String,
    val email: String,
    val phone: String,
    val services: String,
    val budget: String,
    val areaActivity: String,
    val files: List<FileItem>,
)
