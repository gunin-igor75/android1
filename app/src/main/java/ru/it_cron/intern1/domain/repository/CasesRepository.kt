package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.data.model.DataResult

interface CasesRepository<T> {

    suspend fun getCases(): DataResult<T>
}