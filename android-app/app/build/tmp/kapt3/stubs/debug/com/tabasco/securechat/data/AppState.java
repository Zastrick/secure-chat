package com.tabasco.securechat.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\nJ\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\fJ\u001e\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nJ\"\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\nR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R#\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001c"}, d2 = {"Lcom/tabasco/securechat/data/AppState;", "", "()V", "contacts", "Landroidx/compose/runtime/snapshots/SnapshotStateList;", "Lcom/tabasco/securechat/data/Contact;", "getContacts", "()Landroidx/compose/runtime/snapshots/SnapshotStateList;", "conversations", "Landroidx/compose/runtime/snapshots/SnapshotStateMap;", "", "", "Lcom/tabasco/securechat/data/ChatMessage;", "getConversations", "()Landroidx/compose/runtime/snapshots/SnapshotStateMap;", "addContact", "", "alias", "publicId", "addMessage", "contactPublicId", "message", "addReaction", "targetMessageId", "emoji", "updateContactProfile", "name", "photoBase64", "app_debug"})
public final class AppState {
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.runtime.snapshots.SnapshotStateList<com.tabasco.securechat.data.Contact> contacts = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, java.util.List<com.tabasco.securechat.data.ChatMessage>> conversations = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tabasco.securechat.data.AppState INSTANCE = null;
    
    private AppState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.snapshots.SnapshotStateList<com.tabasco.securechat.data.Contact> getContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.snapshots.SnapshotStateMap<java.lang.String, java.util.List<com.tabasco.securechat.data.ChatMessage>> getConversations() {
        return null;
    }
    
    public final void addContact(@org.jetbrains.annotations.NotNull()
    java.lang.String alias, @org.jetbrains.annotations.NotNull()
    java.lang.String publicId) {
    }
    
    /**
     * Actualiza nombre/foto de un contacto cuando nos comparte su perfil.
     * Si el contacto no existía (nos escribió alguien nuevo), lo crea.
     */
    public final void updateContactProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String publicId, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String photoBase64) {
    }
    
    public final void addMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String contactPublicId, @org.jetbrains.annotations.NotNull()
    com.tabasco.securechat.data.ChatMessage message) {
    }
    
    /**
     * Busca el mensaje por id dentro de la conversación con ese contacto
     * (sin importar si lo mandamos nosotros o él) y le agrega el emoji.
     */
    public final void addReaction(@org.jetbrains.annotations.NotNull()
    java.lang.String contactPublicId, @org.jetbrains.annotations.NotNull()
    java.lang.String targetMessageId, @org.jetbrains.annotations.NotNull()
    java.lang.String emoji) {
    }
}