package com.tabasco.securechat.data

import org.json.JSONObject

/**
 * Formato del CONTENIDO del mensaje, antes de cifrarlo.
 *
 * El servidor y RelayClient NO cambian: siguen moviendo un solo string
 * cifrado de un lado a otro. Lo que cambia es que ese string, una vez
 * descifrado, ahora es un JSON con un campo "kind" que dice qué es:
 * texto, imagen, archivo, audio, un aviso de perfil, o una reacción.
 * Así agregamos funciones nuevas sin tocar el protocolo del servidor.
 */
object MessagePayload {

    fun text(id: String, body: String): String =
        JSONObject().apply {
            put("kind", "text")
            put("id", id)
            put("body", body)
        }.toString()

    fun image(id: String, fileName: String, mimeType: String, dataBase64: String): String =
        JSONObject().apply {
            put("kind", "image")
            put("id", id)
            put("fileName", fileName)
            put("mimeType", mimeType)
            put("data", dataBase64)
        }.toString()

    fun file(id: String, fileName: String, mimeType: String, dataBase64: String): String =
        JSONObject().apply {
            put("kind", "file")
            put("id", id)
            put("fileName", fileName)
            put("mimeType", mimeType)
            put("data", dataBase64)
        }.toString()

    fun audio(id: String, dataBase64: String, durationMs: Int): String =
        JSONObject().apply {
            put("kind", "audio")
            put("id", id)
            put("data", dataBase64)
            put("durationMs", durationMs)
        }.toString()

    fun profile(name: String, photoBase64: String?): String =
        JSONObject().apply {
            put("kind", "profile")
            put("name", name)
            if (photoBase64 != null) put("photo", photoBase64)
        }.toString()

    fun reaction(targetId: String, emoji: String): String =
        JSONObject().apply {
            put("kind", "reaction")
            put("targetId", targetId)
            put("emoji", emoji)
        }.toString()
}

/** Resultado de interpretar un payload ya descifrado. */
sealed class ParsedPayload {
    data class Chat(val message: ChatMessage) : ParsedPayload()
    data class Profile(val name: String, val photoBase64: String?) : ParsedPayload()
    data class Reaction(val targetId: String, val emoji: String) : ParsedPayload()
}

fun parsePayload(jsonString: String, fromMe: Boolean): ParsedPayload {
    return try {
        val json = JSONObject(jsonString)
        when (json.optString("kind")) {
            "image" -> ParsedPayload.Chat(
                ChatMessage(
                    id = json.optString("id").ifBlank { java.util.UUID.randomUUID().toString() },
                    fromMe = fromMe,
                    kind = "image",
                    fileName = json.optString("fileName"),
                    mimeType = json.optString("mimeType"),
                    fileBase64 = json.optString("data")
                )
            )
            "file" -> ParsedPayload.Chat(
                ChatMessage(
                    id = json.optString("id").ifBlank { java.util.UUID.randomUUID().toString() },
                    fromMe = fromMe,
                    kind = "file",
                    fileName = json.optString("fileName"),
                    mimeType = json.optString("mimeType"),
                    fileBase64 = json.optString("data")
                )
            )
            "audio" -> ParsedPayload.Chat(
                ChatMessage(
                    id = json.optString("id").ifBlank { java.util.UUID.randomUUID().toString() },
                    fromMe = fromMe,
                    kind = "audio",
                    fileBase64 = json.optString("data"),
                    durationMs = json.optInt("durationMs")
                )
            )
            "profile" -> ParsedPayload.Profile(
                name = json.optString("name"),
                photoBase64 = if (json.has("photo")) json.optString("photo") else null
            )
            "reaction" -> ParsedPayload.Reaction(
                targetId = json.optString("targetId"),
                emoji = json.optString("emoji")
            )
            else -> ParsedPayload.Chat(
                ChatMessage(
                    id = json.optString("id").ifBlank { java.util.UUID.randomUUID().toString() },
                    fromMe = fromMe,
                    kind = "text",
                    text = json.optString("body")
                )
            )
        }
    } catch (e: Exception) {
        // Compatibilidad: si llega algo que no es JSON (mensaje de una
        // versión vieja de la app), lo tratamos como texto plano en vez
        // de fallar.
        ParsedPayload.Chat(ChatMessage(fromMe = fromMe, kind = "text", text = jsonString))
    }
}
