package ru.it_cron.android1.domain.usecases.application

import ru.it_cron.android1.domain.repository.AppRepository

class GetFileItemsUseCase(
    private val repository: AppRepository,
) {
    operator fun invoke() = repository.getFileItems()
}