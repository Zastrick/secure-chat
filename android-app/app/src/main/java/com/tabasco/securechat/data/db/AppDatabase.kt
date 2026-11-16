package com.tabasco.securechat.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos local de SecureChat Tabasco.
 *
 * Limitación documentada: usamos Room con SQLite estándar (sin cifrado).
 * El contenido de los mensajes llega cifrado E2E por la red, pero una vez
 * descifrado y guardado aquí, cualquiera con acceso físico al teléfono
 * podría leer la BD. En una versión de producción se reemplazaría el driver
 * por SQLCipher para cifrar también el almacenamiento local.
 *
 * version = 1: esquema inicial. Si se cambia la estructura de las tablas,
 * hay que aumentar el número y escribir una Migration (o recrear la BD).
 */
@Database(
    entities = [ContactEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "securechat.db"
                ).build().also { INSTANCE = it }
            }
    }
}
