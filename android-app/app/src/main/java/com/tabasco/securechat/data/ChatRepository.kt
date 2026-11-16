package com.tabasco.securechat.data

import android.content.Context
import com.tabasco.securechat.data.db.AppDatabase
import com.tabasco.securechat.data.db.ContactEntity
import com.tabasco.securechat.data.db.MessageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ChatRepository
 *
 * Capa delgada entre la BD Room y AppState (que usa Compose State).
 *
 * Diseño deliberadamente sencillo para un prototipo escolar:
 * - Al arrancar la app, carga contactos y mensajes de Room en AppState.
 * - Cada vez que AppState añade un contacto/mensaje, se persiste en Room.
 * - No hay LiveData ni Flow: usamos corrutinas simples en un scope IO.
 *
 * Si en el futuro se quiere reactividad total (Room → UI sin AppState),
 * bastaría con cambiar los DAOs para devolver Flow<List<...>> y colectarlos.
 */
object ChatRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    // ────────── Carga inicial ──────────────────────────────────────────────

    /** Llámalo UNA VEZ en MainActivity.onCreate para poblar AppState desde la BD. */
    fun loadAll(context: Context) {
        scope.launch {
            val db = AppDatabase.get(context)

            // Contactos
            db.contactDao().getAll().forEach { entity ->
                // addContact ignora duplicados por publicId
                AppState.addContact(entity.alias, entity.publicId)
                // Si tiene foto guardada, la actualizamos en el state
                if (entity.photoBase64 != null) {
                    AppState.updateContactProfile(entity.publicId, entity.alias, entity.photoBase64)
                }
            }

            // Mensajes de cada contacto
            AppState.contacts.forEach { contact ->
                db.messageDao().getByContact(contact.publicId).forEach { entity ->
                    val msg = entity.toChatMessage()
                    // addMessage no duplica si el mensaje ya estuviera (no debería)
                    AppState.addMessage(contact.publicId, msg)
                }
            }
        }
    }

    // ────────── Persistir contacto ─────────────────────────────────────────

    fun saveContact(context: Context, publicId: String, alias: String, photoBase64: String? = null) {
        scope.launch {
            AppDatabase.get(context).contactDao().upsert(
                ContactEntity(publicId = publicId, alias = alias, photoBase64 = photoBase64)
            )
        }
    }

    // ────────── Persistir mensaje ──────────────────────────────────────────

    fun saveMessage(context: Context, contactPublicId: String, message: ChatMessage) {
        scope.launch {
            AppDatabase.get(context).messageDao().insert(message.toEntity(contactPublicId))
        }
    }

    // ────────── Persistir reacción ─────────────────────────────────────────

    fun saveReaction(context: Context, messageId: String, reactions: List<String>) {
        scope.launch {
            AppDatabase.get(context).messageDao()
                .updateReactions(messageId, reactions.joinToString(","))
        }
    }
}

// ────────── Conversores ────────────────────────────────────────────────────

private fun MessageEntity.toChatMessage(): ChatMessage {
    val reactionList = if (reactionsRaw.isBlank()) emptyList()
    else reactionsRaw.split(",").filter { it.isNotBlank() }

    return ChatMessage(
        id = id,
        fromMe = fromMe,
        kind = kind,
        text = text,
        fileName = fileName,
        mimeType = mimeType,
        fileBase64 = fileBase64,
        durationMs = durationMs,
        timestamp = timestamp
    ).also { msg ->
        reactionList.forEach { emoji -> msg.reactions.add(emoji) }
    }
}

private fun ChatMessage.toEntity(contactPublicId: String) = MessageEntity(
    id = id,
    contactPublicId = contactPublicId,
    fromMe = fromMe,
    kind = kind,
    text = text,
    fileName = fileName,
    mimeType = mimeType,
    fileBase64 = fileBase64,
    durationMs = durationMs,
    timestamp = timestamp,
    reactionsRaw = reactions.joinToString(",")
)
