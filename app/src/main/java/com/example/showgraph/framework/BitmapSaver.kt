package com.example.showgraph.framework

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException

object BitmapSaver {

    suspend fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        format: CompressFormat = CompressFormat.PNG,
        quality: Int = 100,
        fileName: String
    ): Result<String> {
        val contentResolver = context.contentResolver
        val mimeType = when (format) {
            CompressFormat.JPEG -> "image/jpeg"
            CompressFormat.PNG -> "image/png"
            CompressFormat.WEBP -> "image/webp"
            else -> "image/png"
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            try {
                val outputStream = contentResolver.openOutputStream(uri)
                outputStream?.use { output ->
                    bitmap.compress(format, quality, output)
                }
            } catch (e: IOException) {
                return Result.failure(e)
            }
            return Result.success(uri.toString())
        }
        return Result.failure(Exception("can't save file"))
    }
}