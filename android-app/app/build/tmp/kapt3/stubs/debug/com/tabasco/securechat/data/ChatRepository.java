package com.tabasco.securechat.data;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ*\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000bJ\u001e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J$\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u000b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/tabasco/securechat/data/ChatRepository;", "", "()V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "loadAll", "", "context", "Landroid/content/Context;", "saveContact", "publicId", "", "alias", "photoBase64", "saveMessage", "contactPublicId", "message", "Lcom/tabasco/securechat/data/ChatMessage;", "saveReaction", "messageId", "reactions", "", "app_debug"})
public final class ChatRepository {
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tabasco.securechat.data.ChatRepository INSTANCE = null;
    
    private ChatRepository() {
        super();
    }
    
    /**
     * Llámalo UNA VEZ en MainActivity.onCreate para poblar AppState desde la BD.
     */
    public final void loadAll(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void saveContact(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String publicId, @org.jetbrains.annotations.NotNull()
    java.lang.String alias, @org.jetbrains.annotations.Nullable()
    java.lang.String photoBase64) {
    }
    
    public final void saveMessage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String contactPublicId, @org.jetbrains.annotations.NotNull()
    com.tabasco.securechat.data.ChatMessage message) {
    }
    
    public final void saveReaction(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> reactions) {
    }
}