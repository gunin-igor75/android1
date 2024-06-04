package ru.it_cron.intern1.domain.usecases.application.budgets

import ru.it_cron.intern1.domain.repository.application.ChoiceItemRepository

class GetSelectedBudgetsUseCase(
    private val repository: ChoiceItemRepository
) {
    operator fun invoke() = repository.getSelectedItems()
}