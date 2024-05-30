package ru.it_cron.android1.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.it_cron.android1.domain.model.contacts.DayWeekItem

interface ContactsRepository {
    fun getDaysItems(): Flow<List<DayWeekItem>>
}