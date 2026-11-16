package com.tabasco.securechat.data;

/**
 * Formato del CONTENIDO del mensaje, antes de cifrarlo.
 *
 * El servidor y RelayClient NO cambian: siguen moviendo un solo string
 * cifrado de un lado a otro. Lo que cambia es que ese string, una vez
 * descifrado, ahora es un JSON con un campo "kind" que dice qué es:
 * texto, imagen, archivo, audio, un aviso de perfil, o una reacción.
 * Así agregamos funciones nuevas sin tocar el protocolo del servidor.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ&\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004J&\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004J\u0018\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004J\u0016\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u0016\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004\u00a8\u0006\u0015"}, d2 = {"Lcom/tabasco/securechat/data/MessagePayload;", "", "()V", "audio", "", "id", "dataBase64", "durationMs", "", "file", "fileName", "mimeType", "image", "profile", "name", "photoBase64", "reaction", "targetId", "emoji", "text", "body", "app_debug"})
public final class MessagePayload {
    @org.jetbrains.annotations.NotNull()
    public static final com.tabasco.securechat.data.MessagePayload INSTANCE = null;
    
    private MessagePayload() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String text(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String body) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String image(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String mimeType, @org.jetbrains.annotations.NotNull()
    java.lang.String dataBase64) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String file(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String mimeType, @org.jetbrains.annotations.NotNull()
    java.lang.String dataBase64) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String audio(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String dataBase64, int durationMs) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String profile(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String photoBase64) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String reaction(@org.jetbrains.annotations.NotNull()
    java.lang.String targetId, @org.jetbrains.annotations.NotNull()
    java.lang.String emoji) {
        return null;
    }
}