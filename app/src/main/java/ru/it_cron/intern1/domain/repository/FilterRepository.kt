package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.data.model.DataResult

interface FilterRepository<T> {

    suspend fun getFilters(): DataResult<T>
}