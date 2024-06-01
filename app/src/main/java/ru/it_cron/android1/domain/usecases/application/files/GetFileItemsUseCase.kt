package ru.it_cron.android1.domain.usecases.application.files

import ru.it_cron.android1.domain.repository.application.FileItemsRepository

class GetFileItemsUseCase(
    private val repository: FileItemsRepository,
) {
    operator fun invoke() = repository.getFileItems()
}