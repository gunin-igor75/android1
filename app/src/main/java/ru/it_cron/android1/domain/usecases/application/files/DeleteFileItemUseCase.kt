package ru.it_cron.android1.domain.usecases.application.files

import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.repository.application.FileItemsRepository

class DeleteFileItemUseCase(
    private val repository: FileItemsRepository,
) {
    operator fun invoke(fileItem: FileItem) = repository.deleteFileItem(fileItem)
}