package ru.it_cron.android1.data.repository.application

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.it_cron.android1.data.model.DataResult
import ru.it_cron.android1.data.model.ErrorBody
import ru.it_cron.android1.data.model.RequestApp
import ru.it_cron.android1.data.network.api.ApiService
import ru.it_cron.android1.domain.model.app.FileItem
import ru.it_cron.android1.domain.repository.application.AppRepository
import java.io.IOException

class AppRepositoryImpl(
    private val apiService: ApiService,
) : AppRepository {

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
                        message = response.errorDto.message, code = response.errorDto.code
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

    private fun createBodyRequest(fileItem: FileItem): MultipartBody.Part {
        val nameContent = fileItem.mimeType.substringBefore("/") + TEXT_CONTENT
        val byteArray = fileItem.byteArray
            ?: throw IllegalStateException("File ${fileItem.nameFile} byteArray is null")
        return MultipartBody.Part.createFormData(
            nameContent,
            fileItem.nameFile,
            byteArray.toRequestBody(fileItem.mimeType.toMediaTypeOrNull(), 0, byteArray.size)
        )
    }

    companion object {
        private const val TEXT_CONTENT = "[content]"
        private const val TAG = "AppRepositoryImpl"
    }
}