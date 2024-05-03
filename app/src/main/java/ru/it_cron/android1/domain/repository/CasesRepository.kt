package ru.it_cron.android1.domain.repository

import ru.it_cron.android1.domain.model.StateApp

interface CasesRepository<T> {

    suspend fun getCases(): StateApp<T>
}