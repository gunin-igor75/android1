package ru.it_cron.intern1.domain.usecases.application.services

import ru.it_cron.intern1.domain.repository.application.ChoiceItemRepository

class ToggleServiceUseCase(
    private val repository: ChoiceItemRepository
) {
    operator fun invoke(resIdName: Int) = repository.toggle(resIdName)
}