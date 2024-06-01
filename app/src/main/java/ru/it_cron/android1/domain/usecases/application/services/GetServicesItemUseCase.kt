package ru.it_cron.android1.domain.usecases.application.services

import ru.it_cron.android1.domain.repository.application.ChoiceItemRepository

class GetServicesItemUseCase(
    private val repository: ChoiceItemRepository
) {
    operator fun invoke() = repository.itemsFlow()
}