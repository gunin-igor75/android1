package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.domain.model.cases.Case

interface CasesRepository<T> {

    suspend fun getCases(): DataResult<T>
    fun getCasesStorage(): List<Case>
}