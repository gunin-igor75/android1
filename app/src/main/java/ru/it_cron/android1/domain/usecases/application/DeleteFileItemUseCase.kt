package ru.it_cron.android1.domain.usecases.application

import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.repository.AppRepository

class DeleteFileItemUseCase(
    private val repository: AppRepository,
) {
    operator fun invoke(fileItem: FileItem) = repository.deleteFileItem(fileItem)
}