package ru.it_cron.android1.domain.usecases.application.areaActivity

import ru.it_cron.android1.domain.repository.application.ChoiceItemRepository

class ClearAllAreaActivityUseCase(
    private val repository: ChoiceItemRepository,
) {
    operator fun invoke() = repository.clearAll()
}