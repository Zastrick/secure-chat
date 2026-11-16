package com.tabasco.securechat.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla de contactos.
 * publicId es la llave pública Base64 del contacto; funciona como PK
 * porque es único e inmutable (no hay cambio de "número").
 */
@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val publicId: String,
    val alias: String,
    /** Foto de perfil en Base64. Puede ser nula si no la ha compartido. */
    val photoBase64: String? = null
)

/**
 * Tabla de mensajes.
 *
 * Nota honesta sobre el esquema: los adjuntos (imágenes, archivos, audio)
 * se guardan como Base64 dentro de esta misma fila. Para un prototipo
 * escolar es suficiente, pero en producción se usaría FileProvider o un
 * directorio de caché en lugar de meter blobs en SQLite.
 */
@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    /** publicId del contacto con quien es esta conversación. */
    val contactPublicId: String,
    val fromMe: Boolean,
    /** "text" | "image" | "file" | "audio" | "system" */
    val kind: String,
    val text: String? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileBase64: String? = null,
    val durationMs: Int? = null,
    val timestamp: Long = System.currentTimeMillis(),
    /**
     * Reacciones guardadas como emojis separados por coma (",").
     * Se deserializa al cargar el mensaje. Sencillo para un prototipo.
     */
    val reactionsRaw: String = ""
)
