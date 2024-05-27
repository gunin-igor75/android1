package ru.it_cron.android1.domain.model.app

import android.net.Uri

data class FileItem(
    val id: String,
    val nameFile: String,
    val size: Long,
    val extension: String,
    val mimeType: String,
    val colorId: Int,
    val isPhoto: Boolean = false,
) {
    var byteArray: ByteArray? = null
    var uri: Uri? = null

    fun getStringSize(): String {
        return if (size >= MB) {
            val mb = size * 1.00 / MB
            String.format("%.2f MB", mb)
        } else {
            val kb = size * 1.00 / KB
            String.format("%.2f KB", kb)
        }
    }

    fun getName(): String {
        return nameFile.substringBefore('.')
    }

    companion object {
        private const val MB = 1_000_000
        private const val KB = 1_000
    }
}


