package ru.it_cron.android1.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.it_cron.android1.domain.model.app.AppItem

interface AppRepository {

    fun getServices(): Flow<MutableList<AppItem>>

    fun getBudgets(): Flow<MutableList<AppItem>>

    fun getAreaActivity(): Flow<MutableList<AppItem>>
}