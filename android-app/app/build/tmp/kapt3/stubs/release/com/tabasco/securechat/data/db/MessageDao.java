package com.tabasco.securechat.data.db;

/**
 * DAO para mensajes.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/tabasco/securechat/data/db/MessageDao;", "", "getByContact", "", "Lcom/tabasco/securechat/data/db/MessageEntity;", "contactId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "message", "(Lcom/tabasco/securechat/data/db/MessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateReactions", "messageId", "reactions", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao()
public abstract interface MessageDao {
    
    /**
     * Devuelve todos los mensajes de una conversación, en orden cronológico.
     */
    @androidx.room.Query(value = "SELECT * FROM messages WHERE contactPublicId = :contactId ORDER BY timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByContact(@org.jetbrains.annotations.NotNull()
    java.lang.String contactId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tabasco.securechat.data.db.MessageEntity>> $completion);
    
    /**
     * Inserta un mensaje nuevo. Si ya existe (por id) lo ignora.
     */
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.tabasco.securechat.data.db.MessageEntity message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Actualiza las reacciones de un mensaje existente.
     */
    @androidx.room.Query(value = "UPDATE messages SET reactionsRaw = :reactions WHERE id = :messageId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateReactions(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    java.lang.String reactions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}