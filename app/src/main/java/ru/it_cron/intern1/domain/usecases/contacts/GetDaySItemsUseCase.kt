package ru.it_cron.intern1.domain.usecases.contacts

import ru.it_cron.intern1.domain.repository.ContactsRepository

class GetDaySItemsUseCase(
    private val repository: ContactsRepository,
) {
    operator fun invoke() = repository.getDaysItems()
}