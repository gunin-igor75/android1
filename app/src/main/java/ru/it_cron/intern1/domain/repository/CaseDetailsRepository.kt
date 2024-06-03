package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.data.model.DataResult

interface CaseDetailsRepository<T> {
    suspend fun getCase(caseId: String): DataResult<T>
}