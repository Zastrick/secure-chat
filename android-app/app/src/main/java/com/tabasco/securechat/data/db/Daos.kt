package com.tabasco.securechat.data.db

import androidx.room.*

/**
 * DAO (Data Access Object) para contactos.
 * Room genera la implementación automáticamente con kapt.
 */
@Dao
interface ContactDao {

    /** Carga todos los contactos. Se llama una vez al iniciar la app. */
    @Query("SELECT * FROM contacts ORDER BY alias ASC")
    suspend fun getAll(): List<ContactEntity>

    /** Inserta o actualiza un contacto (REPLACE descarta la fila anterior). */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(contact: ContactEntity)

    /** Elimina un contacto por su publicId. */
    @Query("DELETE FROM contacts WHERE publicId = :publicId")
    suspend fun delete(publicId: String)
}

/**
 * DAO para mensajes.
 */
@Dao
interface MessageDao {

    /** Devuelve todos los mensajes de una conversación, en orden cronológico. */
    @Query("SELECT * FROM messages WHERE contactPublicId = :contactId ORDER BY timestamp ASC")
    suspend fun getByContact(contactId: String): List<MessageEntity>

    /** Inserta un mensaje nuevo. Si ya existe (por id) lo ignora. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(message: MessageEntity)

    /** Actualiza las reacciones de un mensaje existente. */
    @Query("UPDATE messages SET reactionsRaw = :reactions WHERE id = :messageId")
    suspend fun updateReactions(messageId: String, reactions: String)
}
