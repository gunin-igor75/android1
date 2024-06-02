package ru.it_cron.android1.domain.repository

import ru.it_cron.android1.domain.model.contacts.DayItem

interface ContactsRepository {
    fun getDaysItems(): List<DayItem>
}