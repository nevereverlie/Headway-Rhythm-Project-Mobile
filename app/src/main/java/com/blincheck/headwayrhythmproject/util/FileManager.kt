package com.blincheck.headwayrhythmproject.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.util.Date

class FileManager(private val context: Context) {

    fun copyFileFromGallery(fileUri: Uri): File? {
        runCatching {
            val tempFile = getFileFormat(fileUri)?.let(::getTempFile)
            val inputStream = context.contentResolver.openInputStream(fileUri)
            val outputStream = tempFile?.let(::FileOutputStream)

            inputStream.use {
                outputStream.use {
                    val buffer = ByteArray(BUFFER_SIZE)
                    var read: Int
                    if (inputStream != null && outputStream != null) {
                        while (inputStream.read(buffer).also { read = it } != STREAM_END) {
                            outputStream.write(buffer, 0, read)
                        }
                        outputStream.flush()
                        return tempFile
                    }
                }
            }
        }.onFailure { Log.d("FNP", "Error in file manager") }
        return null
    }

    fun getImageUriForCamera(file: File): Uri = FileProvider.getUriForFile(context, AUTHORITY, file)

    fun getTempFile(fileFormat: String): File? {
        val dir = getTempDir(context.cacheDir)
        return if (dir == null) null else File(dir, "${Date()}$fileFormat")
    }

    private fun getTempDir(dir: File): File? {
        val tempDir = File(dir.path + TEMP_DIR_NAME)
        return if (tempDir.exists() || tempDir.mkdirs()) tempDir else null
    }

    private fun getFileFormat(uri: Uri): String? {
        val mimeType = context.contentResolver.getType(uri)
        return "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }

    companion object {

        private const val BUFFER_SIZE = 1024

        private const val STREAM_END = -1

        private const val AUTHORITY = "com.blincheck.headwayrhythmproject"

        private val TEMP_DIR_NAME = File.separator + "hrp_image" + File.separator

        const val PHOTO_FILE_FORMAT_JPEG = ".jpg"

        const val PHOTO_FILE_FORMAT_PNG = ".png"
    }
}