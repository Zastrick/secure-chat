package com.tabasco.securechat.network;

/**
 * RelayClient
 *
 * Habla con el servidor Node.js por WebSocket. Todo lo que manda por aquí
 * ya viene cifrado desde CryptoManager — este cliente NUNCA ve texto plano
 * saliendo hacia la red, solo blobs cifrados en base64.
 *
 * Incluye reconexión automática: si la conexión se cae (red inestable,
 * el teléfono bloquea la app en segundo plano, etc.), reintenta solo cada
 * pocos segundos en vez de quedarse desconectado para siempre.
 *
 * Cambia SERVER_URL por la IP/dominio donde corras server.js.
 * En emulador Android, "10.0.2.2" apunta al localhost de tu computadora.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00126\u0010\u0005\u001a2\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\u0002\u0010\u0010J\u0006\u0010\u001b\u001a\u00020\u000bJ\u0006\u0010\u001c\u001a\u00020\u000bJ\b\u0010\u001d\u001a\u00020\u000bH\u0002J\b\u0010\u001e\u001a\u00020\u000bH\u0002J\u0016\u0010\u001f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u0003J\u000e\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u0003R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R>\u0010\u0005\u001a2\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/tabasco/securechat/network/RelayClient;", "", "serverUrl", "", "myId", "onMessageReceived", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "from", "ciphertext", "", "onConnected", "Lkotlin/Function0;", "onDisconnected", "onConnecting", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "client", "Lokhttp3/OkHttpClient;", "mainHandler", "Landroid/os/Handler;", "reconnectDelayMs", "", "shouldReconnect", "", "webSocket", "Lokhttp3/WebSocket;", "connect", "disconnect", "openSocket", "scheduleReconnect", "sendCiphertext", "toId", "ciphertextBase64", "updateServerUrl", "newUrl", "app_debug"})
public final class RelayClient {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String serverUrl;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String myId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<java.lang.String, java.lang.String, kotlin.Unit> onMessageReceived = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onConnected = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onDisconnected = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onConnecting = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.Nullable()
    private okhttp3.WebSocket webSocket;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler mainHandler = null;
    private boolean shouldReconnect = true;
    private final long reconnectDelayMs = 3000L;
    
    public RelayClient(@org.jetbrains.annotations.NotNull()
    java.lang.String serverUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String myId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onMessageReceived, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConnected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDisconnected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConnecting) {
        super();
    }
    
    public final void connect() {
    }
    
    /**
     * Actualiza la URL del servidor en caliente (ej. de local a internet wss://...)
     * y reconecta inmediatamente sin reiniciar la app.
     */
    public final void updateServerUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String newUrl) {
    }
    
    private final void openSocket() {
    }
    
    private final void scheduleReconnect() {
    }
    
    public final void sendCiphertext(@org.jetbrains.annotations.NotNull()
    java.lang.String toId, @org.jetbrains.annotations.NotNull()
    java.lang.String ciphertextBase64) {
    }
    
    public final void disconnect() {
    }
}