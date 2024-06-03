package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.data.model.DataResult

interface AccessRepository<T> {

    suspend fun checkAvailableCabinet(): DataResult<T>
}