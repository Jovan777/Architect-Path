package com.example.pmuprojekat.ui.taskcreation

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets
import java.util.UUID

private const val MAX_SCENARIO_TEXT_CHARS = 20_000
private const val MAX_SCENARIO_TEXT_BYTES = 120_000

sealed interface TxtScenarioImportResult {
    data class Success(val text: String) : TxtScenarioImportResult
    data class Error(val message: String) : TxtScenarioImportResult
}

sealed interface DiagramImageImportResult {
    data class Success(val attachment: TaskCreationDiagramImageDraft) : DiagramImageImportResult
    data class Error(val message: String) : DiagramImageImportResult
}

fun readScenarioTxtFile(context: Context, uri: Uri): TxtScenarioImportResult {
    val resolver = context.contentResolver
    val mimeType = resolver.getType(uri)
    val fileName = resolver.queryDisplayName(uri).orEmpty()
    val isTextFile = mimeType == "text/plain" || fileName.endsWith(".txt", ignoreCase = true)
    if (!isTextFile) {
        return TxtScenarioImportResult.Error("Izaberi .txt fajl.")
    }

    return runCatching {
        val bytes = resolver.openInputStream(uri)?.use { input ->
            val output = ByteArrayOutputStream()
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var totalBytes = 0
            while (true) {
                val read = input.read(buffer)
                if (read == -1) break
                totalBytes += read
                if (totalBytes > MAX_SCENARIO_TEXT_BYTES) {
                    return TxtScenarioImportResult.Error("Fajl je predugačak. Skrati tekst i pokušaj ponovo.")
                }
                output.write(buffer, 0, read)
            }
            output.toByteArray()
        } ?: return TxtScenarioImportResult.Error("Fajl nije moguće pročitati kao tekst.")

        val decoder = StandardCharsets.UTF_8
            .newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT)
        val text = decoder.decode(ByteBuffer.wrap(bytes)).toString()
        when {
            text.isBlank() -> TxtScenarioImportResult.Error("Fajl je prazan.")
            text.length > MAX_SCENARIO_TEXT_CHARS ->
                TxtScenarioImportResult.Error("Fajl je predugačak. Skrati tekst i pokušaj ponovo.")
            else -> TxtScenarioImportResult.Success(text)
        }
    }.getOrElse {
        TxtScenarioImportResult.Error("Fajl nije moguće pročitati kao tekst.")
    }
}

fun copyDiagramImageToInternalStorage(context: Context, uri: Uri): DiagramImageImportResult {
    val resolver = context.contentResolver
    val originalFileName = resolver.queryDisplayName(uri).ifBlank { "diagram" }
    val mimeType = resolver.getType(uri) ?: mimeTypeFromFileName(originalFileName)
    if (mimeType !in supportedDiagramMimeTypes) {
        return DiagramImageImportResult.Error("Podržane su PNG, JPG i WEBP slike.")
    }

    return runCatching {
        val extension = extensionForMimeType(mimeType)
        val directory = File(context.filesDir, "task_creation_attachments/diagrams").apply {
            mkdirs()
        }
        val localFileName = "a3_diagram_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(8)}.$extension"
        val target = File(directory, localFileName)

        resolver.openInputStream(uri)?.use { input ->
            target.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: return DiagramImageImportResult.Error("Slika nije mogla da se učita.")

        val sizeBytes = resolver.querySize(uri).takeIf { it > 0L } ?: target.length()
        DiagramImageImportResult.Success(
            TaskCreationDiagramImageDraft(
                originalFileName = originalFileName,
                mimeType = mimeType,
                sizeBytes = sizeBytes,
                localFileName = localFileName,
                localUri = target.toURI().toString(),
                localPath = target.absolutePath
            )
        )
    }.getOrElse {
        DiagramImageImportResult.Error("Slika nije mogla da se učita.")
    }
}

private val supportedDiagramMimeTypes = setOf(
    "image/png",
    "image/jpeg",
    "image/webp"
)

private fun mimeTypeFromFileName(fileName: String): String {
    return when (fileName.substringAfterLast('.', missingDelimiterValue = "").lowercase()) {
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        "webp" -> "image/webp"
        else -> ""
    }
}

private fun extensionForMimeType(mimeType: String): String {
    return when (mimeType) {
        "image/png" -> "png"
        "image/jpeg" -> "jpg"
        "image/webp" -> "webp"
        else -> "img"
    }
}

private fun android.content.ContentResolver.queryDisplayName(uri: Uri): String {
    return query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (index >= 0 && cursor.moveToFirst()) cursor.getString(index).orEmpty() else ""
    }.orEmpty()
}

private fun android.content.ContentResolver.querySize(uri: Uri): Long {
    return query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.SIZE)
        if (index >= 0 && cursor.moveToFirst()) cursor.getLong(index) else -1L
    } ?: -1L
}
