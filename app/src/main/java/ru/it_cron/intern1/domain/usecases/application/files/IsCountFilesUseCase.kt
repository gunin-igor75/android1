package ru.it_cron.intern1.domain.usecases.application.files

import ru.it_cron.intern1.domain.repository.application.FileItemsRepository

class IsCountFilesUseCase(
    private val repository: FileItemsRepository,
) {
    operator fun invoke() = repository.isCountFiles()
}