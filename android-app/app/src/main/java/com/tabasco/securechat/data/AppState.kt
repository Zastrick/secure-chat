package com.tabasco.securechat.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.UUID

data class Contact(
    val alias: String,
    val publicId: String,
    val photoBase64: String? = null
)

/**
 * kind: "text" | "image" | "file" | "audio" | "system"
 * reactions vive como SnapshotStateList para que Compose detecte cuando
 * se le agrega un emoji SIN tener que reemplazar el mensaje completo en
 * la lista de la conversación.
 */
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val fromMe: Boolean,
    val kind: String = "text",
    val text: String? = null,
    val fileName: String? = null,
    val mimeType: String? = null,
    val fileBase64: String? = null,
    val durationMs: Int? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val reactions: SnapshotStateList<String> = mutableStateListOf()
)

object AppState {
    val contacts = mutableStateListOf<Contact>()
    val conversations = mutableStateMapOf<String, MutableList<ChatMessage>>()

    fun addContact(alias: String, publicId: String) {
        if (contacts.none { it.publicId == publicId }) {
            contacts.add(Contact(alias, publicId))
            conversations[publicId] = mutableStateListOf()
        }
    }

    /** Actualiza nombre/foto de un contacto cuando nos comparte su perfil.
     *  Si el contacto no existía (nos escribió alguien nuevo), lo crea. */
    fun updateContactProfile(publicId: String, name: String?, photoBase64: String?) {
        val idx = contacts.indexOfFirst { it.publicId == publicId }
        if (idx >= 0) {
            val current = contacts[idx]
            contacts[idx] = current.copy(
                alias = if (!name.isNullOrBlank()) name else current.alias,
                photoBase64 = photoBase64 ?: current.photoBase64
            )
        } else {
            contacts.add(Contact(alias = name?.ifBlank { publicId.take(8) } ?: publicId.take(8), publicId = publicId, photoBase64 = photoBase64))
            conversations[publicId] = mutableStateListOf()
        }
    }

    fun addMessage(contactPublicId: String, message: ChatMessage) {
        val list = conversations.getOrPut(contactPublicId) { mutableStateListOf() }
        list.add(message)
    }

    /** Busca el mensaje por id dentro de la conversación con ese contacto
     *  (sin importar si lo mandamos nosotros o él) y le agrega el emoji. */
    fun addReaction(contactPublicId: String, targetMessageId: String, emoji: String) {
        val list = conversations[contactPublicId] ?: return
        val msg = list.find { it.id == targetMessageId } ?: return
        if (!msg.reactions.contains(emoji)) {
            msg.reactions.add(emoji)
        }
    }
}
