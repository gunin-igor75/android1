package ru.it_cron.android1.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.data.model.RequestApp
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.model.app.FileItem

interface AppRepository {

    fun getServices(): Flow<MutableList<AppItem>>
    fun getBudgets(): Flow<MutableList<AppItem>>
    fun getAreaActivity(): Flow<MutableList<AppItem>>
    fun getFileItems(): Flow<List<FileItem>>
    fun addFileItem(fileItem: FileItem)
    fun deleteFileItem(fileItem: FileItem)
    fun isCountFiles(): Flow<Boolean>
    suspend fun sendApp(requestApp: RequestApp): DataResult<String>
    fun clearFileItem()
}