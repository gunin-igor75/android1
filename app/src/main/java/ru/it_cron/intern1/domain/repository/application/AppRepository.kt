package ru.it_cron.intern1.domain.repository.application

import ru.it_cron.intern1.data.model.DataResult
import ru.it_cron.intern1.data.model.RequestApp

interface AppRepository {
    suspend fun sendApp(requestApp: RequestApp): DataResult<String>
}