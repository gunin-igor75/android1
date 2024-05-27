package ru.it_cron.android1.domain.usecases.application

import ru.it_cron.android1.domain.repository.AppRepository

class ClearFileItemsUseCase(
    private val repository: AppRepository,
) {
    operator fun invoke() = repository.clearFileItem()
}