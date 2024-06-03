package ru.it_cron.intern1.domain.repository.application

import kotlinx.coroutines.flow.Flow
import ru.it_cron.intern1.domain.model.app.FileItem

interface FileItemsRepository {

    fun getFileItems(): Flow<List<FileItem>>

    fun addFileItem(fileItem: FileItem)

    fun deleteFileItem(fileItem: FileItem)

    fun isCountFiles(): Flow<Boolean>

    fun clearFileItem()
}