package ru.it_cron.android1.data.repository.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.repository.application.FileItemsRepository

class FileItemsRepositoryImpl : FileItemsRepository {

    private val _fileItems: MutableList<FileItem> = mutableListOf()
    private val fileItems: List<FileItem>
        get() = _fileItems.toList()

    private val eventChangeFileItem: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    private val fileItemFlow: Flow<List<FileItem>> = flow {
        eventChangeFileItem.onStart {
            emit(Unit)
        }.collect { emit(fileItems) }
    }

    private val _isCountFiles: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val isCountFiles: StateFlow<Boolean> = _isCountFiles.asStateFlow()

    override fun getFileItems(): Flow<List<FileItem>> {
        return fileItemFlow
    }

    override fun isCountFiles(): Flow<Boolean> {
        return isCountFiles
    }

    override fun addFileItem(fileItem: FileItem) {
        _fileItems.add(fileItem)
        eventChangeFileItem.tryEmit(Unit)
        notifyUpdateFiles()
    }

    override fun clearFileItem() {
        _fileItems.clear()
        notifyUpdateFiles()
    }

    override fun deleteFileItem(fileItem: FileItem) {
        _fileItems.removeIf { it.id == fileItem.id }
        eventChangeFileItem.tryEmit(Unit)
        notifyUpdateFiles()
    }

    private fun notifyUpdateFiles() {
        _isCountFiles.value = _fileItems.size == MAX_COUNT
    }

    companion object {
        private const val MAX_COUNT = 5
    }
}