package ru.it_cron.android1.domain.repository

import ru.it_cron.android1.data.model.DataResult

interface CasesRepository<T> {

    suspend fun getCases(): DataResult<T>
}