package ru.it_cron.intern1.domain.repository

import ru.it_cron.intern1.domain.model.contacts.DayItem

interface ContactsRepository {
    fun getDaysItems(): List<DayItem>
}