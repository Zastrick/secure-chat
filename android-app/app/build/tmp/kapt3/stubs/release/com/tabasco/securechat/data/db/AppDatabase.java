package com.tabasco.securechat.data.db;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/tabasco/securechat/data/db/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "contactDao", "Lcom/tabasco/securechat/data/db/ContactDao;", "messageDao", "Lcom/tabasco/securechat/data/db/MessageDao;", "Companion", "app_release"})
@androidx.room.Database(entities = {com.tabasco.securechat.data.db.ContactEntity.class, com.tabasco.securechat.data.db.MessageEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.tabasco.securechat.data.db.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.tabasco.securechat.data.db.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.tabasco.securechat.data.db.ContactDao contactDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.tabasco.securechat.data.db.MessageDao messageDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/tabasco/securechat/data/db/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/tabasco/securechat/data/db/AppDatabase;", "get", "context", "Landroid/content/Context;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.tabasco.securechat.data.db.AppDatabase get(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}