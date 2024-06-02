package ru.it_cron.android1.domain.usecases.application.budgets

import ru.it_cron.android1.domain.repository.application.ChoiceItemRepository

class GetBudgetsItemUseCase(
    private val repository: ChoiceItemRepository
) {
    operator fun invoke() = repository.itemsFlow()
}