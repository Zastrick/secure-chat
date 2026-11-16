package com.tabasco.securechat.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Nota honesta: como cada archivo va incrustado en Base64 dentro de un
 * único mensaje JSON (sin partirlo en pedazos), este prototipo no está
 * pensado para archivos grandes. Las imágenes se redimensionan antes de
 * enviarse; para archivos genéricos se pone un límite razonable.
 */
object MediaUtils {

    const val MAX_FILE_BYTES = 3 * 1024 * 1024 // 3 MB

    fun fileNameFromUri(context: Context, uri: Uri): String {
        var name = "archivo"
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex >= 0) {
                name = it.getString(nameIndex) ?: name
            }
        }
        return name
    }

    fun mimeTypeFromUri(context: Context, uri: Uri): String =
        context.contentResolver.getType(uri) ?: "application/octet-stream"

    fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.use { it.readBytes() }

    fun bytesToBase64(bytes: ByteArray): String =
        Base64.encodeToString(bytes, Base64.NO_WRAP)

    /** Redimensiona una imagen a un máximo de [maxDimPx] por lado y la comprime a JPEG. */
    fun resizedImageBase64(context: Context, uri: Uri, maxDimPx: Int, quality: Int): String? {
        val input = context.contentResolver.openInputStream(uri) ?: return null
        val original = BitmapFactory.decodeStream(input) ?: return null
        input.close()

        val scale = minOf(1f, maxDimPx.toFloat() / maxOf(original.width, original.height))
        val scaled = if (scale < 1f) {
            Bitmap.createScaledBitmap(
                original,
                (original.width * scale).toInt().coerceAtLeast(1),
                (original.height * scale).toInt().coerceAtLeast(1),
                true
            )
        } else original

        val out = ByteArrayOutputStream()
        scaled.compress(Bitmap.CompressFormat.JPEG, quality, out)
        return Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP)
    }

    fun base64ToBitmap(base64: String): Bitmap? = try {
        val bytes = Base64.decode(base64, Base64.NO_WRAP)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (e: Exception) {
        null
    }
}
