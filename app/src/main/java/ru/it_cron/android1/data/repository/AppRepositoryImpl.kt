package ru.it_cron.android1.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.it_cron.android1.data.model.RequestApp
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.model.app.AppItem.App
import ru.it_cron.android1.domain.model.app.AppItem.Header
import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.repository.AppRepository

class AppRepositoryImpl(
    private val apiService: ApiService,
) : AppRepository {

    private val services: MutableList<AppItem> = mutableListOf(
        Header("Услуги"),
        App("UX-тестирование"),
        App("Дизайн моб. приложения"),
        App("Дизайн веб-интерфейса"),
        App("Веб-разработка и интеграции"),
        App("Разработка моб. приложений"),
        App("Стратегия"),
        App("Креатив"),
        App("Аналитика"),
        App("Тестирование"),
        App("Другое")
    )

    private val budgets: MutableList<AppItem> = mutableListOf(
        Header("Бюджет"),
        App("< 500 тыс.р."),
        App("0.5 - 1 млн.р."),
        App("1 - 3 млн.р."),
        App("5 - 10 млн.р."),
        App("> 10 млн.р.")
    )

    private val areaActivity: MutableList<AppItem> = mutableListOf(
        Header("Откуда вы узнали о нас?"),
        App("Соцсети"),
        App("Рекомендации"),
        App("Работы"),
        App("Рейтинги"),
        App("Рассылка"),
        App("Реклама")
    )

    private val _fileItems: MutableList<FileItem> = mutableListOf()
    private val fileItems: List<FileItem>
        get() = _fileItems.toList()

    private val eventChange: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    private val fileItemFlow: Flow<List<FileItem>> = flow {
        eventChange.emit(Unit)
        eventChange.collect {
            emit(fileItems)
        }
    }

    private val _isCountFiles: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val isCountFiles: StateFlow<Boolean>
        get() = _isCountFiles.asStateFlow()

    override fun getFileItems(): Flow<List<FileItem>> {
        return fileItemFlow
    }

    override fun getServices(): Flow<MutableList<AppItem>> = flow {
        emit(services)
    }

    override fun getBudgets(): Flow<MutableList<AppItem>> = flow {
        emit(budgets)
    }

    override fun getAreaActivity(): Flow<MutableList<AppItem>> = flow {
        emit(areaActivity)
    }

    override fun isCountFiles(): Flow<Boolean> {
        return isCountFiles
    }

    override fun addFileItem(fileItem: FileItem) {
        _fileItems.add(fileItem)
        eventChange.tryEmit(Unit)
        notifyUpdateFiles()
    }

    override fun deleteFileItem(fileItem: FileItem) {
        _fileItems.removeIf { it.id == fileItem.id }
        eventChange.tryEmit(Unit)
        notifyUpdateFiles()
    }

    override suspend fun sendApp(requestApp: RequestApp) {
        val filesBody = requestApp.files.map { createBodyRequest(it) }
        val response = apiService.sendApplication(
            services = requestApp.services,
            budget = requestApp.budget,
            description = requestApp.task,
            files = filesBody,
            name = requestApp.name,
            company = requestApp.company,
            email = requestApp.email,
            phone = requestApp.phone,
            requestFrom = requestApp.areaActivity,
        )
    }

    private fun notifyUpdateFiles() {
        _isCountFiles.value = _fileItems.size == MAX_COUNT
    }

    private fun createBodyRequest(fileItem: FileItem): MultipartBody.Part {
        val nameContent = fileItem.mimeType.substringBefore("/") + TEXT_CONTENT
        val byteArray = fileItem.byteArray
            ?: throw IllegalStateException("File ${fileItem.nameFile} byteArray is null")
        return MultipartBody.Part.createFormData(
            nameContent, fileItem.nameFile,
            byteArray.toRequestBody(fileItem.mimeType.toMediaTypeOrNull(), 0, byteArray.size)
        )
    }

    companion object {
        private const val MAX_COUNT = 5
        private const val TEXT_CONTENT = "[content]"
    }
}