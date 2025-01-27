package ru.it_cron.intern1.domain.interactors.application

import ru.it_cron.intern1.domain.usecases.application.files.AddFileItemUseCase
import ru.it_cron.intern1.domain.usecases.application.files.ClearFileItemsUseCase
import ru.it_cron.intern1.domain.usecases.application.files.DeleteFileItemUseCase
import ru.it_cron.intern1.domain.usecases.application.files.GetFileItemsUseCase
import ru.it_cron.intern1.domain.usecases.application.files.IsCountFilesUseCase

class FileItemsInteractor(
    val fileItems: GetFileItemsUseCase,
    val addFileItem: AddFileItemUseCase,
    val deleteFileItem: DeleteFileItemUseCase,
    val isCountFiles: IsCountFilesUseCase,
    val clearFileItem: ClearFileItemsUseCase,
)
