package ru.it_cron.android1.data.utils

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.it_cron.android1.domain.model.app.FileItem


fun createMultiPart(file: FileItem): MultipartBody.Part {
    val bytes =
        file.byteArray ?: throw IllegalStateException("File ${file.nameFile} byteArray  is nul")
    return MultipartBody.Part.createFormData(
        "file", file.nameFile, bytes.toRequestBody()
    )
}