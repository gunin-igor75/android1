package ru.it_cron.android1.data.repository

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.it_cron.android1.R
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.data.model.ErrorBody
import ru.it_cron.android1.data.model.RequestApp
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.model.app.AppItem.App
import ru.it_cron.android1.domain.model.app.AppItem.Header
import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.repository.AppRepository
import java.io.IOException

class AppRepositoryImpl(
    private val apiService: ApiService,
    context: Context,
) : AppRepository {

    private val services: MutableList<AppItem> = mutableListOf(
        Header(context.getString(R.string.services)),
        App(context.getString(R.string.ui_test)),
        App(context.getString(R.string.design_app)),
        App(context.getString(R.string.design_ui)),
        App(context.getString(R.string.develop_web)),
        App(context.getString(R.string.develop_mobile)),
        App(context.getString(R.string.strategy)),
        App(context.getString(R.string.creative)),
        App(context.getString(R.string.analytics)),
        App(context.getString(R.string.testings)),
        App(context.getString(R.string.other))
    )

    private val budgets: MutableList<AppItem> = mutableListOf(
        Header(context.getString(R.string.budget)),
        App(context.getString(R.string.budget_less)),
        App(context.getString(R.string.budget_05_1)),
        App(context.getString(R.string.budget_1_3)),
        App(context.getString(R.string.budget_5_10)),
        App(context.getString(R.string.budget_10))
    )

    private val areaActivity: MutableList<AppItem> = mutableListOf(
        Header(context.getString(R.string.how_to_know)),
        App(context.getString(R.string.social_network)),
        App(context.getString(R.string.recommendations)),
        App(context.getString(R.string.work)),
        App(context.getString(R.string.ratings)),
        App(context.getString(R.string.newsletter)),
        App(context.getString(R.string.advertising))
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
        Log.d(TAG, fileItem.toString())
        _fileItems.add(fileItem)
        eventChange.tryEmit(Unit)
        notifyUpdateFiles()
    }

    override fun clearFileItem() {
        _fileItems.clear()
        notifyUpdateFiles()
    }

    override fun deleteFileItem(fileItem: FileItem) {
        _fileItems.removeIf { it.id == fileItem.id }
        eventChange.tryEmit(Unit)
        notifyUpdateFiles()
    }

    override suspend fun sendApp(requestApp: RequestApp): DataResult<String> {
        val filesBody = requestApp.files.map { createBodyRequest(it) }
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.sendApplication(
                    services = requestApp.services,
                    budget = requestApp.budget,
                    description = requestApp.task,
                    files = filesBody,
                    name = requestApp.name,
                    company = requestApp.company,
                    email = requestApp.email,
                    phone = requestApp.phone,
                    requestFrom = requestApp.areaActivity
                )
                if (response.errorDto == null) {
                    DataResult.Success(response.data)
                } else {
                    val errorBody = ErrorBody(
                        message = response.errorDto.message,
                        code = response.errorDto.code
                    )
                    Log.e(TAG, errorBody.toString())
                    DataResult.Error(error = response.errorDto.message)
                }
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
                DataResult.ErrorInternet()
            }
        }
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
        private const val TAG = "AppRepositoryImpl"
    }
}