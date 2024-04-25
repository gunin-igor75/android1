package ru.it_cron.android1.domain.repository

import ru.it_cron.android1.domain.model.AvailableState

interface AccessRepository {

    suspend fun checkAvailableCabinet(): AvailableState
}