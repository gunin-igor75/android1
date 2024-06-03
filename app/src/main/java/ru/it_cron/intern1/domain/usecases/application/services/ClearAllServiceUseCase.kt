package ru.it_cron.intern1.domain.usecases.application.services

import ru.it_cron.intern1.domain.repository.application.ChoiceItemRepository

class ClearAllServiceUseCase(
    private val repository: ChoiceItemRepository
) {
    operator fun invoke() = repository.clearAll()
}