package ru.it_cron.android1.domain.repository

import ru.it_cron.android1.data.model.DataResult

interface AccessRepository<T> {

    suspend fun checkAvailableCabinet(): DataResult<T>
}